(function() {

    CodeMirror.extendMode("css", {
        commentStart: "/*",
        commentEnd: "*/",
        newlineAfterToken: function(type, content) {
            return /^[;{}]$/.test(content);
        }
    });

    CodeMirror.extendMode("javascript", {
        commentStart: "/*",
        commentEnd: "*/",
        // FIXME semicolons inside of for
        newlineAfterToken: function(type, content, textAfter, state) {
            if (this.jsonMode) {
                return /^[\[,{]$/.test(content) || /^}/.test(textAfter);
            } else {
                if (content == ";" && state.lexical && state.lexical.type == ")") return false;
                return /^[;{}]$/.test(content) && !/^;/.test(textAfter);
            }
        }
    });

    CodeMirror.extendMode("xml", {
        commentStart: "<!--",
        commentEnd: "-->",
        newlineAfterToken: function(type, content, textAfter) {
            return type == "tag" && />$/.test(content) || /^</.test(textAfter);
        }
    });

    // Comment/uncomment the specified range
    CodeMirror.defineExtension("commentRange", function (isComment, from, to) {
        var cm = this, curMode = CodeMirror.innerMode(cm.getMode(), cm.getTokenAt(from).state).mode;
        cm.operation(function() {
            if (isComment) { // Comment range
                cm.replaceRange(curMode.commentEnd, to);
                cm.replaceRange(curMode.commentStart, from);
                if (from.line == to.line && from.ch == to.ch) // An empty comment inserted - put cursor inside
                    cm.setCursor(from.line, from.ch + curMode.commentStart.length);
            } else { // Uncomment range
                var selText = cm.getRange(from, to);
                var startIndex = selText.indexOf(curMode.commentStart);
                var endIndex = selText.lastIndexOf(curMode.commentEnd);
                if (startIndex > -1 && endIndex > -1 && endIndex > startIndex) {
                    // Take string till comment start
                    selText = selText.substr(0, startIndex)
                        // From comment start till comment end
                        + selText.substring(startIndex + curMode.commentStart.length, endIndex)
                        // From comment end till string end
                        + selText.substr(endIndex + curMode.commentEnd.length);
                }
                cm.replaceRange(selText, from, to);
            }
        });
    });

    // Applies automatic mode-aware indentation to the specified range
    CodeMirror.defineExtension("autoIndentRange", function (from, to) {
        var cmInstance = this;
        this.operation(function () {
            for (var i = from.line; i <= to.line; i++) {
                cmInstance.indentLine(i, "smart");
            }
        });
    });

    // Applies automatic formatting to the specified range
    CodeMirror.defineExtension("autoFormatRange", function (from, to) {
        var cm = this;
        var outer = cm.getMode(), text = cm.getRange(from, to).split("\n");
        var state = CodeMirror.copyState(outer, cm.getTokenAt(from).state);
        var tabSize = cm.getOption("tabSize");

        var out = "", lines = 0, atSol = from.ch == 0;
        function newline() {
            out += "\n";
            atSol = true;
            ++lines;
        }

        for (var i = 0; i < text.length; ++i) {
            var stream = new CodeMirror.StringStream(text[i], tabSize);
            while (!stream.eol()) {
                var inner = CodeMirror.innerMode(outer, state);
                var style = outer.token(stream, state), cur = stream.current();
                stream.start = stream.pos;
                if (!atSol || /\S/.test(cur)) {
                    out += cur;
                    atSol = false;
                }
                if (!atSol && inner.mode.newlineAfterToken &&
                    inner.mode.newlineAfterToken(style, cur, stream.string.slice(stream.pos) || text[i+1] || "", inner.state))
                    newline();
            }
            if (!stream.pos && outer.blankLine) outer.blankLine(state);
            if (!atSol) newline();
        }

        cm.operation(function () {
            cm.replaceRange(out, from, to);
            for (var cur = from.line + 1, end = from.line + lines; cur <= end; ++cur)
                cm.indentLine(cur, "smart");
            cm.setSelection(from, cm.getCursor(false));
        });
    });

    CodeMirror.defineExtension("formatJson", function (json, options) {
        var reg = null,
            formatted = '',
            pad = 0,
            PADDING = '    ';
        options = options || {};
        options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
        options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;
//
//        var reg = /\ +/g;
//        var a=editor1.getValue().replace(reg," ");
        if (typeof json !== 'string') {
            json = JSON.stringify(json);
        } else {
//            json = JSON.parse(json);
//            json = JSON.stringify(json);
        }
        reg = /[\ \ \ \ \r\n]+/g;
        json = json.replace(reg, '');
        reg = /([\{\}])/g;
        json = json.replace(reg, '\r\n$1\r\n');
        reg = /([\[\]])/g;
        json = json.replace(reg, '\r\n$1\r\n');
        reg = /(\,)/g;
        json = json.replace(reg, '$1\r\n');
        reg = /[\r\n]+/g;
        json = json.replace(reg, '\r\n');
        reg = /\r\n\,/g;
        json = json.replace(reg, ',');
        reg = /[\ \ \ \ ]+/g;
        json = json.replace(reg, '');
        if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
            reg = /\:\r\n\{/g;
            json = json.replace(reg, ':{');
            reg = /\:\r\n\[/g;
            json = json.replace(reg, ':[');
        }
        if (options.spaceAfterColon) {
            reg = /\:/g;
            json = json.replace(reg, ':');
        }
        (json.split('\r\n')).forEach(function (node, index) {
                var i = 0,
                    indent = 0,
                    padding = '';

                if (node.match(/\{$/) || node.match(/\[$/)) {
                    indent = 1;
                } else if (node.match(/\}/) || node.match(/\]/)) {
                    if (pad !== 0) {
                        pad -= 1;
                    }
                } else {
                    indent = 0;
                }

                for (i = 0; i < pad; i++) {
                    padding += PADDING;
                }
//                    if(!(node.match(/\d,/) || node.match(/\",/))){
//                        formatted += padding + node;
//                    }else{
//                        formatted += padding + node + '\r\n';
//                    }
                formatted += padding + node + '\r\n';
                pad += indent;
            }
        );
        reg = /[\r\n]+/g;
        formatted = formatted.replace(reg, '\r\n');
        return formatted;
    });
})();
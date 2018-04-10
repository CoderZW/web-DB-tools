#!/bin/bash

domain_name=${1}
country_name=${2}
state=${3//_re_/ }
locality_name=${4//_re_/ }
organization_name=${5//_re_/ }
organizational_unit_name=${6//_re_/ }
common_name=${domain_name}
email_address=$7


domain_key=${domain_name}.key
openssl genrsa -out $domain_key 2048

domain_csr=${domain_name}.csr

/usr/bin/expect <<-EOF
set time 30
spawn openssl req -new -key ${domain_key} -out ${domain_csr}

expect {
    "*Country*" {
        send "${country_name}\r"
        exp_continue
    }
    "*Province*" {
            send "${state}\r"
            exp_continue
    }
    "*Locality*" {
            send "${locality_name}\r"
            exp_continue
    }
    "*Organizational*" {
                send "${organizational_unit_name}\r"
                exp_continue
    }
    "*Organization*" {
                send "${organization_name}\r"
                exp_continue
    }
    "*Common*" {
                send "${common_name}\r"
                exp_continue
    }
    "*Email*" {
                    send "${email_address}\r"
                    exp_continue
    }
    "*password*" {
                        send "\r"
                        exp_continue
    }
    "*optional*" {
                        send "\r"
                        exp_continue
    }
}

expect eof
EOF
echo "create csr!"
domainInfo=${domain_name}.info
echo ${domain_name} > ${domainInfo}
echo ${country_name} >> ${domainInfo}
echo ${state} >> ${domainInfo}
echo ${locality_name} >> ${domainInfo}
echo ${organization_name} >> ${domainInfo}
echo ${organizational_unit_name} >> ${domainInfo}
echo ${common_name} >> ${domainInfo}
echo ${email_address} >> ${domainInfo}

echo ${domain_key}
echo ${domain_csr}

cd ssl/
if [ -d ${domain_name} ]; then
newD=${domain_name}$(date "+%Y-%m-%d_%H:%M:%S")
mv ${domain_name} ${newD}
fi


mkdir ${domain_name}
cd ${domain_name}
mv ../../${domain_key} .
mv ../../${domain_csr} .
mv ../../${domainInfo} .

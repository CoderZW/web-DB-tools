package com.homethy.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * errorCode错误码规范:<br/>
 * 1. 范围: 除了OK之外,所有的错误码的值都必须是6位十进制整数,错误码的取值范围都必须是100000~999999<br/>
 * 2. 顺序: 所有的错误码在本类的出现顺序必须按递增的方法排序<br/>
 * 3. 从左到右各位的含义<br/>
 * a) 第1,2位表示功能块编码,第1位不能为0,10表示公共错误,其它业务分别自己递增添加<br/>
 * b) 第3~6位为真正的错误码,从0000开始往上累加<br/>
 * c) 例如: 100000<br/>
 * 4. 对此规范有任何疑问或建议,请联系xiaolong.zhang3@renren-inc.com
 */
public enum ErrorCodeEnum {
    // 0默认错误
    OK(0, "Success"), //
    UNKNOWN_ERROR(100000, "Server Error"),
    SIG_ERROR(100001, "Sig Error"),
    SESSION_ERROR(100002, "Session Error"),
    RETURN_NULL_ERROR(100003, "Return Null"),
    DB_ERROR(100004, "DB Error"),
    NO_AUTH(100005, "No Authority"),
    INVALID_REQUEST_ERROR(100006, "Invalid Request Error"),
    // 1.1 Exception
    EXCEPTION_FIELD_NULL(110001, "Field %s can't be null or empty string"),
    EXCEPTION_FIELD_SCOPE_ERROR(110002, "FIELD %s "),
    EXCEPTION_PARA_ERROR(110003, "Pamater %s formate error"),

    // 2 - Transaction 相关
    MEMBERISEMPTY(200001, "Member doesn't exist"), //
    ADDMEMBERFAILED(200002, "Failed to add member, please try again"), //
    TRANSACTION_NAME_IS_NULL(200003, "Please enter transaction name"), //
    CREATE_TRANSACTION_FAILED(200004, "Failed to create transaction, please try again"), //
    GET_TRANSACTION_FAILED(200005, "Failed to load transaction detail, please try again"), //
    GCREATE_TRANSACTION_NAME_IS_NULL(200006, "Please enter name"), //
    TRANSACTION_PHOTO_IS_NULL(200007, "Please add transaction photo"), //
    CREATE_PHOTO_IS_NULL(200008, "Failed to add photo"), //
    ADD_MEMBER_LOST_PARAM(200009, "Please complete the form "), //
    CREATE_TRANSACTION_FAILED_WITH_CREATE_MEMEBER_FAILED(200010, "Failed to create transaction"), //
    MEMBER_ALLREADY_EXIST(200011, "Already exists"),
    TRANSACTION_IS_NOT_EXIST(200012, "Transaction doesn't exist"),
    MEMBER_NOT_IN_TRANSACTION(200013, "Member doesn’t exist"),
    USER_NOT_IN_TRANSACTION(200014, "You're not in this transaction"),
    USER_CANNOT_GET_MEMBER_INFO(200015, "No access to the member's file"),
    USER_HAVE_NO_MEMBER_TO_SHARE(200016, "No one to share the member with"),
    MEMBER_CANNOT_ADD_SELF(200017, "Can't add yourself"),
    TRANSACTION_ADDRESS_IS_NULL(200018, "Please enter transaction address"), //
    ADD_MEMBER_TEAM_USER_NOT_EXIST(200019, "Team user doesn’t  exist"),
    USER_CANNOT_EDIT_MEMBER_INFO(200020, "No right to edit the member's file"),

    // 2.1 Transaction Task相关
    // ADD_SUPERTASK_FAILED(210001, "Add superTask failed"),
    ADD_TASK_FAILED(210002, "Failed to add task"),
    // EDIT_SUPERTASK_FAILED(210003, "Failed to edit superTask"),
    EDIT_TASK_FAILED(210003, "Failed to edit task"),
    UPDATE_FINISH_FLAG_FAILED(210004, "Failed to complete task "),
    // DELETE_SUPERTASK_FAILED(210005, "Failed to delete superTask"),
    DELETE_TASK_FAILED(210006, "Failed to delete task"),
    VA_TASK_NUM_FAILED(210007, "Failed to load task number"),
    RENAME_TASK_FAILED(210008, "Failed to rename task"),
    ASSIGN_TASK_FAILED(210009, "Failed to assign task"),
    UN_ASSIGN_TASK_FAILED(210010, "Failed to unassign task"),
    SET_TASK_DEADLINE_FAILED(210011, "Failed to set task deadline"),
    INIT_TASK_WITH_TEMPLATE_FAILED(210011, "Failed to add the task"),

    // 2.2 Transaction Template相关
    ADD_TEMPLATE_FAILED(220001, "Failed to add template "),
    GET_TEMPLATE_FAILED(220002, "Failed to load template"),
    EDIT_TEMPLATE_FAILED(220003, "Failed to edit template"),
    LIST_TEMPLATE_FAILED(220004, "Failed to load template list"),
    LIST_SYSTEM_TEMPLATE_FAILED(220005, "Failed to load system template list"),
    RENAME_TEMPLATE_FAILED(220006, "Failed to rename template"),

    // 3 - 注册
    REG_EMAIL_ALREADY_TAKEN(300002, "Email already taken"),
    REG_PASSWORD_IS_NULL(300003, "Password must between 6 and 20 characters"),
    REG_NAME_IS_NULL(300004, "First Name and Last Name are required"),
    REG_SUCCESS_LOGIN_FAIL(300005, "Register successful but login failed, please login again."),
    REG_USER_FAILED(300006, "Failed to register "),
//    REG_USER_ACCOUNT_ID_NOT_MATCH(300007, "Register email not match the id"),
    REG_EMAIL_ALREADY_LEAD(300008, "Email is already taken "),
    EMAIL_ALREADY_REG_AGENT(300009, "This email address already exists as an agent"),
    EMAIL_ALREADY_REG_LENDER(300010, "This email address already exists as a lender"),
    EMAIL_ALREADY_TEAM_LEAD(300011, "This email address already exists as a lead"),

    // 3.1 - 登录
    LOGIN_ACCOUNT_PWD_ERROR(310001, "Your email or password is incorrect"),
    LOGIN_ACCOUNT_NOT_EXIST(310002, "Your email is not registered"),
    LOGIN_ACCOUNT_NOT_ACTIVATE(310003, "Please check your email to activate your account "),
    LOGIN_GET_TICKET_FAIL(310004, "Failed to login"),
    LOGIN_WITH_ACTIVATE_USER(310005, "Please activate your account"),
    LOGIN_SEND_ACTIVATE_MAIL_EXCESS(310006, "You have made too many requests today, please try it tomorrow"),
    LOGOUT_FAILED(310007, "Failed to logout"),
    LOGIN_THIRD_DB_ERROR(310008, " Failed to login by third-party’s account "),
    LOGIN_THIRD_FAILED(310009, " Failed to login by third-party’s account "),
    LOGIN_THIRD_VERIFY_FAILED(310010, "Failed to verify third-party's account"),
    LOGIN_LENDER_FAILED(310011, "Your account temporarily isn't authorized to use Chime's app"),
    LOGIN_LENDER_DELETED(310012, "Sorry! Your Chime account is deleted by the admin user"),
    LOGIN_BROKER_FAILED(310013, "Your account temporarily isn't authorized to use Chime's app"),

    // 3.2 - 续票
    TICKEY_RENEW_USERINFO_IS_NULL(320001, "Can't get user's info"),

    // 3.3 - user
    USER_FIRST_NAME_IS_EMPTY(330001, "First Name is Required"),
    USER_LAST_NAME_IS_EMPTY(330002, "Last Name is Required"),
    USER_NOT_EXIST(330003, "User doesn't exist"),
    USER_PASSWORD_ERROR(330005, "Password error, try again"),
    USER_CHANGE_PWD_FAIL(330006, "Change password failed"),
    USER_NEW_PWD_EQUAL_OLD_PWD(330007, "Please set a different password"),
    USER_HEAD_URL_IS_EMPTY(330008, "Head url is required"),
    USER_PERMISSION_DENY(330009, "No right."),
    USER_PHONE_EMPTY(330010, "Phone number cannot be empty"),
    USER_CHANGE_PWD_CURRENT_PASSWD_INCORRECT(330011, "The current password is incorrect"),
    EMAIL_EXIST_IN_TEAM(330012, "The email address exists across your team's CRM."),
    PHONE_EXIST_OF_USER(330013, "This phone number is duplicate"),
    USER_PHONE_IS_EMPTY(330014, "Phone Number is Required"),
    USER_EMAIL_IS_EMPTY(330015, "Email is Required"),
    FIRST_NAME_INVILED(330016, "First Name can not be longer than 30 characters."),
    LAST_NAME_INVILED(330017, "Last Name can not be longer than 30 characters."),
    USER_TIMEZONE_ID_IS_EMPTY(330018, "Timezone id is Required"),

    // 3.4 - 激活
    ACTIVATE_CODE_IS_EMPTY(340001, "Activate code is required"),
    ACTIVATE_CODE_ERROR(340002, "Your activate code is incorrect"),

    // 4 - 联系人
    CONTACT_ALREADY_EXIST(400002, "Contact already exists"),
    CONTACT_NOT_EXIT(400003, "Contact doesn't exist."),
    CONTACT_NAME_IS_NULL(400004, "First Name is required"),
    CONTACT_ID_FORMATE_ERROR(400005, "Contact doesn't exist"), // 联系人id错误，可能是从String转换成int出错
    CONTACT_CANNOT_ADD_SELF(400006, "Can't add yourself"),
    CONTACT_LIST_IS_NULL(400007, "Contact list is null"),
    CONTACT_LIST_FORMAT_ERROR(400008, "Contact list format error"),
    CONTACT_EMAIL_NOT_MATCH_ID(400009, "Contact email dones't match id"),
    CONTACT_ALREADY_EXIST_IN_TEAM(400010, "Contact already exists in team"),
    CONTACT_ALREADY_EXIST_IN_LEADGROUP(400011, "Contact already exists in lead group"),
    CONTACT_ADDLIST_PARSE_ERROR(400012, "Get user email info failed"),

    // 4.1 联系人tag
    CONTACT_TAG_NOT_EXIST(410001, "Contact tag doesn't exist"),
    CONTACT_TAG_SYSTEM_CANNOT_DEL(410002, " Can't delete system tag"),
    CONTACT_TAG_ALREADY_EXIST(410003, "Tag already exists"),
    CONTACT_TAG_NAME_IS_NULL(410004, "Tag name can't be null"),
    CONTACT_TAG_SYSTEM_CANNOT_EDIT(410005, "Can't edit system tag"),

    // 5 - 签名
    PARAM_ABCENT(500001, "Absent parameter, Please check and correct it"),
    PARAM_FORMAT_ERROR(500002, "Invalid parameter format"),
    ADD_RECIPIENT_FAIL(500003, "Add recipient to envelope failed."),
    REQUEST_SIGNATURE_FAIL(500004, "Request new signature failed."),
    DOWNLOAD_FILE_FAIL(500005, "Download file to signature failed."),
    NO_SIGN_WITH_DOC(500006, "No signature found with doc."),
    NO_AUTH_WITH_ENVELOPE(500007, "You have no auth to edit envelope create by others."),
    LOGIN_TO_DOCUSIGN_FAILED(500008, "Login to docusign failed. "),

    // 6- 文档
    DOC_MULTIPARTFILE_ERROR(600000, "Failed to Load file content"),
    DOC_S3_UPLOAD_ERROR(600001, "Upload to S3 failed"),
    DOC_S3_DOWNLOAD_ERROR(600002, "Download from S3 failed"),
    DOC_S3_BUCKET_ERROR(600003, "Create bucket in S3 failed"),
    DOC_DROPBOX_UPLOAD_ERROR(600004, "Failed to upload from Dropbox"),
    DOC_RENAME_ERROR(600005, "Invalid name format"),

    DOC_RENAME_SAVE_ERROR(600006, "Cannot include special characters"),

    DOC_DROPBOX_REPLACE_ERROR(600007, "Failed to Replace by Dropbox"),

    DOC_DB_ERROR(600008, "Connect to DB failed"),
    DOC_THUMBNAIL_ERROR(600009, "Transfer from pdf to thumbnial failed"),
    DOC_NORESULT(600010, "Can't find any result from DB"),
    DOC_TEMPLATE_NORESULT(600016, "Can't find any templates from DB"),
    DOC_TEMPLATEDOC_NORESULT(600017, "Can't find any doc templates from DB"),

    DOC_GOOGLEDRIVE_UPLOAD_ERROR(600018, "Failed to upload from GoogleDrive"),
    DOC_GOOGLEDRIVE_REPLACE_ERROR(600019, "Failed to replace by GoogleDrive"),

    DOC_SHARE_MEMBER_ERROR(600020, "Failed to share document to member"),

    DOC_FORBIDDEN_ERROR(600021, "No authorization to view the document"),

    // 7 - Lead
    LEAD_PARAM_ERROR(700000, "Invalid information, Please correct it."),
    // 7.1 Lead Template相关
    ADD_LEAD_TEMPLATE_FAILED(710001, "Failed to add template"),
    GET_LEAD_TEMPLATE_FAILED(710002, "Failed to load template"),
    UPDATE_LEAD_TEMPLATE_FAILED(710003, "Failed to update template"),
    LIST_LEAD_TEMPLATE_FAILED(710004, "Failed to load template list"),
    DELETE_LEAD_TEMPLATE_FAILED(710005, "Failed to delete template"),

    // 7.2 Lead Task相关
    ADD_LEAD_TASK_FAILED(720001, "Add lead task failed"),
    GET_LEAD_TASK_FAILED(720002, "Load lead task failed"),
    UPDATE_LEAD_TASK_FAILED(720003, "Update lead task failed"),
    LIST_LEAD_TASK_FAILED(720004, "Load lead task list failed"),
    DELETE_LEAD_TASK_FAILED(720005, "Delete lead task failed"),
    ADD_LEAD_TASK_NO_AUTHORITY(720006,"A Task cannot be added. You haven't gained access to the lead."),

    // 7.3 lead tag相关
    TAG_NOT_EXIST(730001, "Cannot find the tag"),
    TAG_SIZE_TO_LONG(730002, "Enter within 30 characters"),
    
    // 7.5 lead header相关
    LEAD_HEADER_NOT_EXIST(750001, "lead header info not exists"),
    LEAD_HEADER_PARAM_ERROR(750002, "header info is null or empty"),
    
    // 7.6 lead 通讯录（来电显示）相关
    LEAD_CONTACT_PARAM_ERROR(760001, "parameter curPage or pageSize is invalid"),
    LEAD_CONTACT_V2_PARAM_ERROR(760002, "parameter limit is invalid"),

    // 7- lead
    LEAD_NAME_IS_NULL(700002, "First Name and Last Name are required"),
    LEAD_NOT_EXIST(700003, "You haven't gained access to the lead"),
    LEAD_LIST_IS_NULL(700004, "Lead list is empty"),
    LEAD_LIST_FORMAT_ERROR(700005, "Lead list format error"),
    ACTIVITY_NOT_EXIST(700006, "Activity doesn't exist"),
    REQUEST_NOT_EXIST(700007, "Request doesn't exist"),
    LEAD_ALREADY_CONVERTED(700008, "Lead has been converted"),
    LEAD_CONVERT_FAIL(700009, "Failed to convert lead"),
    REQUEST_ALREADY_ADDED(700010, "Request is already added"),
    LEAD_EMAIL_CAN_NOT_BE_SAME_WITH_AGENT(7000011, "Lead's email can't be the same with agent"),
    YOU_CAN_NOT_ADD_YOURSELF(700012, "Cannot add yourself"),
    LEAD_NO_AUTH(700013, "No authority"),
    LEAD_SOCIAL_INFO_FAILED(700014, "Failed to get lead social information"),
    LEAD_FIRST_NAME_IS_NULL(700015, "Lead first name is required"),
    ADD_LEAD_FAILED(700016, "Failed to add lead"),
    LEAD_REPORT_USERID_FORMAT_ERROR(700017, "User ID list format error"),
    LEAD_LIST_FAILED(700018, "Failed to list lead"),
    GET_LEAD_FILTER_FROM_JSON_FAILED(700019, "Failed to get lead filter, please try again"),
    LEAD_SORT_TYPE_ERROR(700020, "Unidentified sort type"),
    LEAD_IS_TRASH(700021, "Not allowed to edit since lead is trash"),
    LEAD_FILTER_NOT_EXIST(700022, "Lead filter doesn't exists"),
    ADD_LEAD_ASSIGNED_USER_NOT_EXIST(700023, "The assigned user doesn't exists"),
    LEAD_ALREADY_EXISTS(7000024, "Lead already existed"),
    LEAD_PAGE_SIZE_ERROR(7000025, "Current page or page size error"),
    LEAD_IS_AGENT(7000026, "Lead is an agent"),
    LEAD_TAG_LIST_IS_EMPTY(7000027, "Please select a tag to continue."),
    LEAD_GROUP_LIST_IS_EMPTY(7000028, "Please select a group to continue."),
    LEAD_GROUPS_CONTAINS_UNAUTHORIZED_GROUP(7000028, "Groups contain invalid ones that you have no right to operate."),
    LEAD_TAGS_CONTAINS_UNAUTHORIZED_TAG(7000029, "Tags contain invalid ones that you have no right to operate."),
    LEADINQUIRE_FIELD_TO_UPDATE_NOTEXIST(7000030,"Invalid field"),
    LEADINQUIRE_FIELD_TO_UPDATE_FAILED_TO_SET(7000031,"Wrong format"),
    LEAD_FIELD_TO_UPDATE_NOTEXIST(7000032,"Invalid field"),
    LEAD_FIELD_TO_UPDATE_FAILED_TO_SET(7000033,"Wrong format"),
    LEAD_ALREADY_AGENT(7000034,"The email has existed as an agent, please change one"),
    LEAD_IMPORT_SAVE_FILE_FAILED(7000036,"Failed to save the file you upload."),
    LEAD_IMPORT_BINDING_LENGTH_NOT_MATCH_COLUMN_LENGTH(7000037,"Binding length does not match column length."),
    LEAD_IMPORT_UNKNOWN_BINDING(7000038,"Binding not found."),
    LEAD_IMPORT_LACK_REQUIRED_BINDINGS(7000038,"Binding not found."),
    LEAD_IMPORT_LACK_REQUIRED_FIELDS(7000039,"Required field is empty."),
    LEAD_IMPORT_RECORD_NOT_FOUND(7000040,"Lead import record not found."),
    LEAD_IMPORT_PARSE_BINDING_JSON_ERROR(7000041,"Parse bindings json error."),
    LEAD_IMPORT_PARSE_ROWS_JSON_ERROR(7000042,"Parse bindings json error."),
    LEAD_IMPORT_ANALYZE_FILE_FAILED(7000043,"Failed to analyze the file you upload, please check your file and upload again."),
    LEAD_IMPORT_SAVE_GROUP_INFO_FAILED(7000044,"Failed to save groups info."),
    LEAD_IMPORT_NEED_GOOGLE_CONTACT_AUTH(7000045,"Need Google Contact authorization"),
    LEAD_IMPORT_GET_GOOGLE_CONTACT_GROUP_FAILED(7000046,"Get contact groups failed"),
    LEAD_IMPORT_GET_GOOGLE_CONTACTS_FAILED(7000047,"Get contacts failed"),
    TRASH_LEAD_FAILED(7000048,"Failed to trash the lead"),
    LEAD_FILTER_NAME_SIZE_ERROR(7000049, "Filter name must less than 30 characters"),
    SYS_FILTER_CANNOT_DEL(7000050, "System filter can not be deleted"),
    FILTER_DELETE_FAILED(7000051, "You can not delete this filter"),
    LEAD_EMAIL_LIST_FORMAT_ERROR(7000052, "Email format error"),
    LEAD_PHONE_LIST_FORMAT_ERROR(7000053, "Phone format error"),
    LEAD_SET_PRIMARY_PHONE_MORE_THAN_ONE(7000054, "Cannot set primary phone more than one"),
    LEAD_SET_PRIMARY_EMAIL_MORE_THAN_ONE(7000055, "Cannot set primary email more than one"),
    LEAD_PHONE_FORMATE_ERROR(700001, "Invalid phone number"),
    LEAD_PHONE_TOO_LONG(7000056, "The maximum length of a phone number is 20 numbers"),
    LEAD_FILTER_NAME_EXIST(7000057, "The name has been used"),
    LEAD_FILTER_NAME_BLANK(7000058, "Name cannot be blank"),

    RECOVER_TRASH_LEAD_FAILED(7000060,"Failed to recover the trashLead"),
    RRASE_TRASH_LEAD_FAILED(7000061,"Failed to erase the trashLead"),
    HAS_ASSIGNEDTO_PAYLENDER(7000062,"This lead has assigned to a pay lender"),
    REFUSE_ASSIGNTO_LENDER(7000063,"This lead refuse to share his info with any lenders"),
//    LEAD_EAMIL_HAS_BEAN_ADD_ALREADY(7000027, "Lead email has already bean added"),

    LEAD_DEAL_SIZE_SAVE_FAILED(7000064,"Saved unsuccessfully, please try again"),
    LEAD_FILTER_OPTIONS_OVERSIZE(7000065, "Too many options selected"),
    LEAD_EXIST_IN_PRIVATE_SYSTEM(7000066, "This email address cannot be added. It already exists in another agent's private leads."),

    LEAD_IMPORT_ROWS_TOO_MUCH(7000068,"This file contains too many rows. Files must not exceed 4000 rows. "),
    LEAD_IMPORT_SELECTED_FIELD_TOO_MUCH(7000069,"This column is already selected, you cannot select it again."),
    LEAD_IMPORT_NOT_FINISHED_IMPORT(7000070,"Data is currently being imported, we cannot provide a download right now."),
    LEAD_IMPORT_TEAM_IS_IMPORTING(7000071," A team member is currently importing leads, please wait a moment."),
    LEAD_PHONE_STATE_ERROR(7000072, "Lead phone state error"),
    LEAD_IMPORT_HISTORY_CANNOT_DELETE(7000073, "Import history can't be deleted."),
    LEAD_IMPORT_EMPTY_FILE(7000075, "Imported file must contain more than 1 rows, please check your file and try again."),
    LEAD_CONTACT_IMPORT_FAILED(7000076, "Import lead contact failed."),

    // 8- Site
    SITE_SEARCH_TEXT_CAN_NOT_BE_EMPTY(800001, "Search text can't be empty"),
    SOCIAL_TYPE_NULL(800002, "Social type field is empty"),
    ADD_SITETYPE_PARAMS_ERROR(800003, "Please check information"),
    USER_NULL_ERROR(800004, "User is null"),

    SITELIST_PAGE_PARAM_ERROR(800005, "PageNo or displayNum can't < 0"),

    LAUNCH_STATUS_SETUP_ERROR(800006, "Site launch status set up is error!"),
    TEMPLATE_NULL_ERROR(800007, "Template style is empty"),
    TEMPLATE_IS_ERROR(800008, "Template is empty"),

    SITE_TITLE_NULL_ERROR(800009, "Site title is empty"),
    SITE_TAGLINE_NULL_ERROR(800010, "Site tagline is null"),
    SITE_LOGO_NAME_NULL_ERROR(800011, "Site logoname is null"),
    EMAIL_ADDRESS_FORMAT_ERROR(800012, "Email address error"),
    SITE_SOURCE_ERROR(800013, "Site source error"),
    TRACKING_CONTENT_NULL_ERROR(800014, "Lead tracking content is null error"),

    // 9 - import gmail lead
    NOT_OAUTH_GMAIL_ERROR(900001, "User not oauth gmail"),
    HAS_ALREADY_OAUTH_GOOGLE(900002, "User has already oauth google"),
    REVOKE_GOOGLE_OAUTH_FAILED(900003, "Revoke google oauth failed. please try again"),
    REFRESH_USER_ACCESS_TOKEN_FAILED(900004, "Refresh user access token failed"),
    HAS_ALREADY_OAUTH_GOOGLE_SCOPE(900005, "User has already oauth %s"),
    GOOGLE_OAUTH_SCOPE_NOT_EXIST(900006, "The scope to oauthrize is not exist."),
    
    // 10 - chat remind　
    INVITEER_IS_NOT_EXIST(1000001, "The lead doesn't exist"),
    
    // 11 -reset password
    THE_EMAIL_IS_NOT_REGISTERED(1100001, "The email isn't registered"),
    
    //12 - team
    TEAM_ACCOUNT_EXIST_ERROR(1200002, "Sorry! This email address is already in use by another account."),
    TEAM_ACCOUNT_IS_LEAD_ERROR(1200003, "This account is a lead"),
    TEAM_MEMBER_INSERT_ERROR(1200004, "Failed to add team member"),
    TEAM_MEMBER_AUTH_ERROR(1200005, "This member doesn't exist"),
    TEAM_MEMBER_STATUS_ERROR(1200006, "Failed to update team members’ status"),
    TEAM_MEMBER_IS_NOT_EXIST(1200008, "Team member does not exist"),
    TEAM_NOT_EXIST(1200009, "Team doesn't exist"),
    TEAM_MEMBER_NO_RIGHT_TO_ADD(1200010, "You cannot invite a team member"),
    MEMBER_NOT_IN_TEAM(1200011, "You're not in any team"),
    MEMBER_NOT_INVITED(1200012, "You're not invited"),
    MEMBER_ALREADY_AGENT(1200013, "This account is already an agent."),
    TEAM_ACCOUNT_IS_INVITED(1200014, "This account is a lead or have invited"),
    TEAM_MEMBER_INVITED_TWICE(1200015, "Sorry, you cannot add the same team member twice."),
    TEAM_MEMBER_ALREADY_LEAD(1200016, "Sorry, you cannot add your lead into the team."),
    TEAM_MEMBER_ALREADY_EXIST(1200017, "You can not invite the same team member twice."),
    CANNOT_DELETE_TEAM_MEMBER(1200018, "Sorry, you cannot delete a team member"),
    NORMAL_MEMBER_CANNOT_DELETED(1200019, "A normal member cannot deleted"),
    TEAM_MEMBER_FIRST_NAME_INVILED(1200020, "First Name can not be longer than 30 characters."),
    TEAM_MEMBER_LAST_NAME_INVILED(1200021, "Last Name can not be longer than 30 characters."),
    TEAM_MEMBER_PHONE_INVILED(1200021, "Please input a valid phone."),

    // 13 group
    GROUP_ALREADY_EXIST(1300001, "Group already exists"),
    GROUP_NOT_EXIST_IN_TEAM(1300002, "Group does not exist"),
    GROUP_NO_RIGHT_TO_ADD(1300003, "You cannot add a group"),
    GROUP_ID_INVALID(1300004, "Invalid group ID"),
    GET_GROUP_LIST_DATA_FAILED(1300005, "Failed to get group list"),
    MOVE_GROUP_FAILED(1300006, "Failed to move group"),
    GROUP_TEAM_CONTACT_CANNOT_EDIT(1300007, "Contacts or team group cannot edit"),
    GROUP_NO_RIGHT_TO_DELETE(1300008, "You cannot delete a group"),
    GROUP_NAME_CANNOT_EMPTY(1300009, "Group name can't be empty"),
    GROUP_NAME_REPEATED(1300010, "Group name repeated"),
    GROUP_NAME_TOO_LONG(1300011, "Group name mush be less than 30 characters"),
    GROUP_NO_RIGHT_TO_EDIT(1300012, "You cannot edit the groups"),
    GROUP_NO_RIGHT_TO_MOVE(1300013, "You cannot move the groups"),

    // 14 send email
    SEND_EMAIL_ERROR(1400001, "Failed to send email"),
    EMAIL_NOT_EXIST_ERROR(1400001, "Email doesn't exist"),

    // 15 gather agent info
    GATHER_AGENTINFO_PARAM_ERROR(1500001, "Param error"),
    GATHER_AGENTINFO_INVALID_AGENTINFOURL(1500002, "Invalid agent info url error"),
    GATHER_AGENTINFO_INVALID_INITEMAIL(1500003, "Invalid init email"),
    GATHER_AGENTINFO_INVALID_PASSPORT(1500004, "Invalid generate url passport"),
    
    // 16 appointment
    APPOINTMENT_NOT_EXIST(1600001, "Appointment does not exist"),
    APPOINTMENT_ADD_NO_OAUTH(1600002, "An appointment can't be added. You haven't gained access to the lead."),
    
    // 17 Drip mail related
    EMAIL_GROUP_NOT_EXIST(1700001, "Email group does not exist."),

    // 18 stage
    STAGE_ALREADY_EXIST(1800001, "Stage already exists in team"),
    STAGE_NOT_EXIST_IN_TEAM(1800002, "Stage does not exist in team"),
    STAGE_NO_RIGHT_TO_ADD(1800003, "You cannot add a stage"),
    STAGE_ID_INVALID(1800004, "Invalid stage ID"),
    GET_STAGE_LIST_DATA_FAILED(1800005, "Failed to get stage list"),
    MOVE_STAGE_FAILED(1800006, "Failed to move stage"),
    STAGE_TEAM_CONTACT_CANNOT_EDIT(1800007, "Contacts or team stage cannot be edited"),
    STAGE_NO_RIGHT_TO_DELETE(1800008, "You cannot delete stage"),
    STAGE_NAME_CANNOT_EMPTY(1800009, "Pipeline name can't be empty"),
    STAGE_IS_NOT_IN_YOUR_TEAM(1800010, "The stage does not belong to your team."),
    STAGE_TRASH_STILL_HAVE_LEAD(1800011, "You cannot delete pipeline '%s', which still have leads inside."),
    STAGE_NO_RIGHT_TO_EDIT(1800012, "You can not edit this stage"),
    STAGE_NO_RIGHT_TO_MOVE(1800013, "You cannot change the pipeline order"),
    STAGE_NAME_TOO_LONG(1800014, "Pipeline name must be less than 20 characters"),
    STAGE_NAME_REPEATED(1800015, "Pipeline name repeated"),
    MUST_HAVE_A_STAGE(1800016, "To add a lead, you need to add a pipeline besides the default one."),
    STAGE_NAME_ALREADY_EXIST(1800001, "Stage '%s' already exists. Please use a different name"),

    //19 dripmail
    DRIPMAIL_LACK_OF_TEAMID(1900001, "Lack of team id"),
    LACK_OF_DRIPMAIL_ID(1900002, "Lack of drip template group id"),
    RECEIPT_ADDR_NOT_FOUND(1900003, "Receipt address is not found"),
    RECEIPT_NOT_FOUND(1900004, "Cannot find the receipt user list"),
    DRIPMAIL_GROUP_NOT_FOUND(1900005, "Dripmail is not found"),
    LACK_OF_BODYCONTENT(1900006, "Lack of content"),
    LACK_OF_SUBJECT(1900007, "Lack of subject"),
    LEAD_NO_MATC_APPLICATION(1900008, "Lead does not match application"),
    TEMPLATE_SOURCE_NOT_EXIST(1900009, "Template source does not exist"),
    AGENT_NOT_EXIST(1900010, "Agent does not exist."),
    LACK_OF_LEAD_ID(1900013, "Lack of lead id."),
    AUTO_ASSIGNED_RULE_NOT_EXIST(1900014, "Auto assigned rule does not exist"),
    AUTO_ASSIGNED_RULE_HAS_EXISTED(1900015, "Auto assigned rule has existed"),
    DRIP_MAIL_HAS_EXISTED(1900016, "Drip campaign template group has been assigned"),
    AGENT_EMAIL_ADDR_NOT_FOUND(1900017, "Sorry, you don't have Chime email account. Please go to Settings to create one."),
    LEAD_EMAIL_ADDR_NOT_FOUND(1900018, "This lead does not have email address."),
    AGENT_HAS_NO_PHONE(1900019, "You need to activate Virtual Number."),
    LEAD_HAS_NO_PHONE(1900020, "This lead does not have phone number."),
    SEARCH_UPDATE_DRIP_FAILED(1900021, "Search update drip failed."),
    GET_LEADUSER_UPDATE_LOCK_FAILED(1900022, "Get LeadUser update Lock failed."),
    AGENT_TEAM_MEMBER_HAS_NO_PHONE(1900023, "To send drip text, please activate virtual number for this team member."),

    //191:dripmail task
    DRIPMAIL_TASK_NOT_EXIST(1910001, "Drip mail task not exist."),
    DRIPMAIL_TASK_NOT_MATCH_WITH_RECEIPT(1910002, "Dripmail task not match with receipt."),

    //192:dripmail application
    DRIPMAIL_HAS_BEEN_STOPED(1920001, "You've stopped this series of drip campaign."),
    DRIP_CAMPAIGN_APPLICATION_NOT_FOUNT(1920002, "Drip campaign application not found."),
    CANNOT_REUSE(1920003, "Cannot reuse this application."),
    NO_LEAD_SELECTED(1920005, "Please select one lead at least."),
    ILLEGAL_REF_ID(1920006, "Illegal RefId in Drip Application."),

    //193:dripmail emails
    DRIPMAIL_EMPTY_QUERYID(1930001,"Empty queryId."),

    //194:dripmail watcher
    DRIPMAIL_WATCHER_NOT_EXIST(1940001, "Drip mail watcher does not exist."),

    //195:common email template
    MANUAL_EMAIL_TEMPLATE_NOT_EXIST(1950001, "Template does not exist."),
    NO_EDIT_AUTHORITY(1950002, "You are not authorized to perform this operation."),
    HAS_TAG_NEED_FILLED(1950003, "Tags cannot be empty."),
    ILLEGAL_TAG_NAME(1950004, "Illegal tag name."),
    AGENT_HAS_TAG_NEED_FILLED(1950005, "%s hasn't filled out the info for %s"),
    LEAD_TAG_NEED_FILLED(1950006, "There's no info for the lead tag %s"),
    TEAM_HAS_NO_SITE(1950007, "There's no info for the team tag %s"),
    TEMPLATE_DISABLE(1950008, "The drip campagin templates applied has been disabled."),
    MUST_BE_POSITIVE_NUMBER(1950009, "Only positive numbers are allowed"),

    //196:offline email
    OFFLINE_TASK_NOT_EXIST(1960001, "Offline task not exist"),

    //197:dripmail teamplate
    TEMLATE_DISABLE(1970001, "The drip campagin templates applied has been disabled."),
    TEMPLATE_GROUP_NOT_FOUND(1970002, "Drip campaign template group not found."),
    PREV_TEMPLATE_NOT_FOUND(1970010, "Prev template not found."),
    CUR_TEMPLATE_NOT_FOUND(1970011, "Current template not found."),
    DRIPMAIL_TEMPLATE_EDITING(1970012, "Drip template is being edited."),
    DRIP_CAMPAIGN_NAME_NOT_FOUND(1970013, "Drip Campaign Name absent."),
    DRIPMAIL_TEMPLATE_NOT_FOUND(1970014, "Drip campaign template not found."),
    DRIPMAIL_TEMPLATE_EMPTY(1970015, "Drip campagin template cannot be empty."),

    //198:auto campaign
    AUTO_CAMPAIGN_HAS_BEEN_PAUSE(1980001, "Auto campaign rule is disable now."),
    AUTO_CAMPAIGN_ABSENT_TEMPLATE(1980002, "Please add a drip campaign template."),
    OPERATE_TOO_FAST(1980003, "Operation cannot be processed. Please wait for completion and try again."),
    LACK_OF_GROUP(1980004, "Please note one or two group(s) you had chosen originally has been deleted during the suspension; please confirm the auto campaign with the group(s) left."),
    LACK_OF_PIPELINE(1980005, "Please note one or two pipelines(s) you had chosen originally has been deleted during the suspension; please confirm the auto campaign with the pipelines(s) left."),
    LACK_OF_SOURCE(1980006, "Please note one or two source(s) you had chosen originally has been deleted during the suspension; please confirm the auto campaign with the source(s) left."),

    //20 lead alert
    ALERT_SEARCH_CONDITION_EXIST(2000001, "Property alert already exists."),
    ALERT_SEARCH_CONDITION_NOT_EXIST(2000002, "Property alert is not exist."),
    ALERT_LISTING_INFO_TOO_MUCH(2000003, "You can't send more than 25 listings at a time."),
    ALERT_AGENT_EMAIL_NOT_EXIST(2000004, "Please connect your Gmail account first to be able to send emails."),
    ALERT_LEAD_EMAIL_NOT_EXIST(2000005, "No email has been added to this lead."),
    ALERT_FUNCTION_NOT_AVAILABLE(2000006, "Sorry, this function is not available"),
    ALERT_LEAD_EMAIL_UNSUBSCRIBE(2000007, "The lead has unsubscribed property alerts"),
    ALERT_SCHEDULE_FORMAT_ERROR(2000008, "Sorry, schedule format error"),

    //21 zillow
    ZILLOW_ACCESS_KEY_NOT_FOUND(2100000,"access key is not found"),
    ZILLOW_SECRETE_KEY_NOT_FOUND(2100001,"secrete key is not found"),
    ZILLOW_LEAD_CONTENT_IS_EMPTY(2100002,"lead message is empty"),
    ZILLOW_LEAD_CONTENT_JSON_ERROR(2100003,"lead message is not a valid json string"),
    ZILLOW_MESSAGE_TYPE_IS_NULL(2100004,"lead message type is null"),
    ZILLOW_MESSAGE_ID_IS_NULL(2100005,"lead message id is null"),
    ZILLOW_SNS_MESSAGE_IS_NULL(2100006,"zillow sns message is null"),
    ZILLOW_AGENT_NOT_FOUND(2100007,"zillow agent does not exist"),
    ZILLOW_LEAD_EMAIL_IS_EMPTY(2100008,"zillow lead email is empty"),
    ZILLOW_LEAD_NAME_IS_EMPTY(2100009,"zillow lead name is empty"),
    ZILLOW_LEAD_ALREADY_EXIST(2100010,"zillow lead already exists"),
    ZILLOW_ADD_LEAD_FAILED(2100011,"zillow add lead failed"),
    ZILLOW_NO_LEAD_IS_SPECIFIED(2100012,"no lead is specified"),
    ZILLOW_LEAD_ALREADY_IMPORTED(2100013,"zillow lead already exists"),
    ZILLOW_CONTACT_MESSAGE_IS_EMPTY(2100014,"zillow contact message is empty"),
    ZILLOW_CONNECT_TEAMID_IS_EMPTY(2100015,"zillow user id or team id is empty"),
    ZILLOW_CONNECT_STATUS_IS_CLOSED(2100016,"zillow connect status is closed"),
    ZILLOW_TEAM_ADMIN_NOT_EXISTS(2100015,"team admin not found"),
    ZILLOW_TEAM_HAVE_ALREADY_IMPORT_THE_LEAD(2100016,"one team can only import the same zillow client once "),
    ZILLOW_CONNECT_SWITCH_FAILED(2100019, "failed to switch connection status."),
    ZILLOW_AGENT_NOT_IN_WHITE_LIST(2100020, "agent not in whitelist."),
    ZILLOW_NO_SUCH_AGENT(2100021, "no such agent."),
    ZILLOW_EMPTY_LENDER_CONTACT(2100022, "the lender contact is null."),
    ZILLOW_LENDER_CONTACT_TYPE_NOT_ALLOW(2100023, "this lender contact type not allowed for this agent"),

    //22 payment cardinfo gather
    GATHER_PAYMENT_CARDINFO_PARAM_ERROR(2200001, "Param error."),
    GATHER_PAYMENT_CARDINFO_UPDATE_ERROR(2200002, "Update cardinfo error."),
    AGENT_INFO_ALREADY_EXIST(2200003, "agent already exist"),
    AGENT_INFO_CREATE_FAILED(2200004, "agent info create failed"),
    AGENT_INFO_DOES_NOT_EXIST(2200005, "agent info does not exist"),
    SERVICE_ORDER_ALREADY_EXIST(22000006, "service order already exist"),
    SERVICE_ORDER_CREATE_FAILED(2200007, "service order create failed"),
    SERVICE_ORDER_DOES_NOT_EXIST(2200008, "service order does not exist"),
    SERVICE_ORDER_PARSER_ORDERITEM_FAILED(2200008, "service order parser orderitem failed "),
    SERVICE_ORDER_HAS_BEAN_CLOSED(2200009, "service order has bean closed "),
    AGENT_CARD_INFO_HAS_BEAN_GATHERED(2200010, "agent cardinfo has been gathered "),
    SERVICE_PLAN_INIT_VIA_ORDER_FAILED(2200011, "service plan init via order failed"),
    SERVICE_PLAN_UPDATE_FAILED(2200012, "service plan update failed"),
    SERVICE_PLAN_COMPLETE_FAILED(2200013, "service plan complete failed"),
    SERVICE_PLAN_TERMINATE_FAILED(2200014, "service plan complete failed"),
    SERVICE_ORDER_ITEM_LIST_SHOULD_NOT_BE_EMPTY(2200015, "service order item should not be empty"),
    SERVICE_ORDER_SEND_EMAIL_ERROR(2200016, "service order send email error "),
    GATHER_PAYMENT_BILLINGADDRESS_ADD_ERROR(2200017, "Failed to add billing address."),
    GATHER_PAYMENT_CARDINFO_FIELD_INVALID(2200018, "Payment pay card info field invalid."),
    GATHER_PAYMENT_CARDNUMBER_TOO_LONG(2200019, "The card number is too long."),

    SERVICE_SUBSCRIPTION_PACKAGE_ID_ERROR(2200020, "Illegal service package ID"),
    SERVICE_BUYER_PACKAGE_ID_ERROR(2200021, "Illegal buyer package ID"),
    SERVICE_SELLER_PACKAGE_ID_ERROR(2200022, "Illegal seller package ID"),

    SERVICE_NOT_PURCHASE_ERROR(2200023, "Service package not purchased"),

    SERVICE_UPDATE_ORDER_ILLEGAL_ERROR(2200024, "Illegal order ID update"),

    SERVICE_SUBSCRIPTION_PACKAGE_MEMBER_COUNT_EXCEED_ERROR(2200025, "Your team member count exceeds the limit of the subscribed service package."),
    SERVICE_UPDATE_ERROR(2200026, "You need to upgrade at least one service package."),

    TEAM_MEMBER_ROLE_ERROR(2200027, "Only team owners can buy services"),

    SERVICE_UPDATE_SUBSCRIPTION_ERROR(2200028, "To upgrade, you must choose a service package that is higher than the last one."),

    SERVICE_UPDATE_BUYER_ERROR(2200029, "To upgrade, you must choose a buyer package that is higher than the last one."),

    SERVICE_UPDATE_SELLER_ERROR(2200030, "To upgrade, you must choose a seller package that is higher than the last one."),

    SERVICE_OLD_ORDER_ERROR(2200031, "Expired order ID"),

    SERVICE_BASIC_PACKAGE_ERROR(2200032, "Buying a CRM is mandatory."),

    SERVICE_BASIC_PACKAGE_BOUGHT_ERROR(2200033, "You have bought Package."),

    SERVICE_PACKAGE_ID_ERROR(2200034, "Illegal package ID"),

    SERVICE_FREE_TRIAL_FAILED_ERROR(2200035, "Free trial start failed"),

    SERVICE_PACKAGE_HAS_BOUGHT_ERROR(2200036,"You can not purchase the same package twice."),

    SERVICE_LISTING_PACKAGE_ID_ERROR(2200037, "Illegal listing package ID"),
    SERVICE_RETENTION_PACKAGE_ID_ERROR(2200038, "Illegal retention package ID"),
    SERVICE_DUPLICATE_REQUESTS_ERROR(2200039,"Please do not submit duplicate requests."),

    SERVICE_PAYMENT_ORDER_ILLEGAL_ERROR(2200040, "Illegal order ID"),

    SERVICE_DUPLICATE_PACKAGE_ERROR(2200041,"Please do not submit duplicate package."),

    SERVICE_BOUGHT_ANOTHER_ORDER_ERROR(2200042,"The order is not valid or has expired."),

    SERVICE_UPDATE_TO_LOW_ERROR(2200043, "Sorry, you can only upgrade your existing package to a higher package, but not downgrade to a lower package."),

    SERVICE_ORDER_TOTAL_REAL_PRICE_ERROR(2200044,"The order 's total real price is illegal."),

    PAYMENT_ORDER_DELETE_STATUS_ILLEGAL_ERROR(2200045, "Only unpaid order can be deleted."),

    PAYMENT_ORDER_STOP_STATUS_ILLEGAL_ERROR(2200046, "Only paid order can be stoped."),

    PAYMENT_ORDER_SKIP_STATUS_ILLEGAL_ERROR(2200047, "Only paid order can be skiped."),

    PAYMENT_ORDER_SKIP_DATE_ILLEGAL_ERROR(2200048, "Paid order can be skiped after five days of payment failure."),

    PAYMENT_ORDER_PRICE_EDIT_ERROR(2200049, "This order can not be edited."),

    //22 lender bind card
    BINDCARD_LENDER_LENDERID_EMPTY(2200049, "Lender bind card lenderid not present."),

    //23 team authority
    TEAM_MEMBER_LIMITED_ERROR(2300001, "Your team member count reached the upper limit."),
    TEAM_MEMBER_FT_LIMITED_ERROR(2300002, "Sorry, you can only add five team members in free trial. UPGRADE your package to let more team members in!"),
    TEAM_MEMBER_PAYED_LIMITED_ERROR(2300003, "Sorry, the number of members is limited. UPGRADE your package to let more team members in!"),
    TEAM_MEMBER_FT_EXPIRED_ERROR(2300004, "Sorry, your subscription has expired. If you would like to continue using our functions, you " +
            "may subscribe on our web version. "),
    TEAM_MEMBER_PAYED_EXPIRED_ERROR(2300005, "Sorry, your subscription has expired. If you would like to continue using our functions, you " +
            "may subscribe on our web version. "),
    TEAM_MEMBER_UNACTIVITY_ERROR(2300006, "To unlock functions for free trial, please go to billing page on the web version of Chime to add your credit card."),
    TEAM_MEMBER_APP_FT_EXPIRED_ERROR(2300007, "Your Chime free trial has ended. Upgrade now to continue growing your business."),

    //24 action plan
    ACTIONPLAN_NO_TASK(2400001, "task should not be empty"),

    //25 payment
    PAYMENT_CARDINFO_WRONG_ID(2500000, "Payment card info field invalid."),
    PAYMENT_CARDINFO_FIELD_INVALID(2500001, "Payment card info field invalid."),
    PAYMENT_CARDINFO_ADD_DUPLICATE(2500002, "You can't add the same card twice."),
    PAYMENT_CARDINFO_DELETE_ERROR(2500003, "Failed to delete card."),
    PAYMENT_CARDINFO_AUTHORIZE_ERROR(2500004, "Failed to authorize payment card info."),
    PAYMENT_CARDINFO_CREATE_ERROR(2500005, "Failed to create payment card info."),
    PAYMENT_BILLINGADDRESS_ADD_ERROR(2500006, "Failed to add payment billing address."),
    PAYMENT_BILLINGADDRESS_DEL_ERROR(2500007, "Failed to delete payment billing address."),
    PAYMENT_BILLINGADDRESS_UPDATE_ERROR(2500008, "Failed to update payment billing address."),
    PAYMENT_BILLINGADDRESS_ZIPCODE_ERROR(2500009, "Invalid Zipcode."),
    PAYMENT_FAILURE_ERROR(2500010, "Payment error."),
    FREQUENT_BILLING_RETRY_SUCCESS(2500013, "Payment successful."),

    //26 nexmo
    SWITCH_USING_NEXMO_FAILED(2600001,"Failed to switch on/off virtual number"),
    SWITCH_SENDING_NEWLEAD_SMS_FAILED(2600002,"Failed to switch on/off auto welcome messages"),
    AGENT_CALL_LEAD_FAILED(2600003,"Failed to make the call. Please contact Chime Customer Service for technical support"),
    PROVIDE_EXIST_VIRTUAL_NUMBER_TO_AGENT_FAILED(2600004,"Failed to apply for a virtual number"),
    AGENT_CALL_LEAD_PHONE_INVALID(2600005,"To dial with virtual number, please correct your real phone number in your profile(App Homepage > 'More' tab > top area)"),
    NEWLEAD_SMS_CONTENT_LENGTH_OVER_LIMIT(2600006, "Maximum number of characters allowed is %s."),
    UPDATE_NEWLEAD_SMS_CONTENT_FAILED(2600007,"Error! Your edits failed due to service errors, please try again."),
    NEWLEAD_SMS_CONTENT_CONTAINS_CHAR_NOT_SUPPORT(2600008,"Some special punctuation marks and characters are not allowed."),
    AWM_NO_RIGHT_TO_OPERATE(2600009,"You don’t have authority to edit."),
    AWM_NO_RECORD_FOUND(2600010,"The record not found."),
    AWM_PARAM_INVALID(2600011,"Param is invalid."),
    AWM_INVALID_TEMPLATE(2600012, "Invalid template."),
    AWM_INVALID_START_TIME(2600013, "Invalid start time."),
    AWM_INVALID_END_TIME(2600014, "Invalid end time."),
    AWM_NAME_CANNOT_BE_NULL(2600015, "Template name can't be blank.  "),
    AWM_CONTENT_CANNOT_BE_NULL(2600016, "Template content can't be blank. "),
    OPEN_VN_NEED_AGREE_TERM(26000017, "You first have to agree to the terms below, before you can use the virtual number function."),
    OPEN_VN_NEED_APPLY_FIRST(26000018, "You first have to apply the virtual number function."),
    OPEN_VN_NO_RIGHT(26000019, "You have no right to do the operation."),
    OPEN_VN_ONLY_ADMIN_CAN_SWITCH_TEAM_MEMBERS_VN(26000020, "Only team's administrator can to do the operation."),
    //
    VIRTUAL_NUM_SET_CALLRECORD_PARAM_ERROR(2600021, "Params invalid."),
    VIRTUAL_NUM_SET_CALLRECORD_FAILED(2600022, "Failed to turn on/off call record."),
    VCALL_NEED_OPEN_VIRTUAL_NUMBER_FIRST(26000023, "You first have to enable virtual number function first."),
    VCALL_OPEN_VN_NEED_APPLY_FIRST(26000024, "You first have to apply the virtual number function."),
    VCALL_DO_NOT_HAVE_A_PHONE_NUMBER(26000025, "You do not have a phone number."),
    VCALL_VIRTUAL_NUMBER_SERVICE_IS_NOT_AVAILABLE(26000026, "Virtual number service is not available right now."),
    VCALL_SERVER_ERROR(26000027, "Server error."),
    VCALL_DOWNLOAD_RECORDING_FILE_FAILED(26000028, "Download recording file failed."),
    VCALL_UPLOAD_RECORDING_FILE_FAILED(26000029, "Upload recording file failed."),
    VCALL_NO_TO_NUMBER_SPECIFIED(26000030, "Please select the phone number you wanna call."),

    //
    NEXMO_UNKNOWN_PACKAGE_INFO(26000031,"Unkonwn package info."),
    AWM_GET_NULL_INFO(26000032,"get null vsmsTemplate or lead or leadUser."),
    AWM_NO_RIGHT_TO_GET_INFO(2600033,"You don’t have authority to get vsms template."),
    NEXMO_NUMBER_APPLY_REQUEST_PARAM_INVALID(2600034,"Apply request parameters are invalid."),
    NEXMO_NUMBER_APPLY_REQUEST_SUBMIT_FAILED(2600035,"Apply request submission failed."),
    NEXMO_NUMBER_APPLY_NO_NUMBERS(2600036,"No number available right now."),
    NEXMO_NUMBER_APPLY_BUY_NUMBER_FAILED(2600037,"Buy number failed."),
    NEXMO_NUMBER_APPLY_SETTLEMENT_FAILED(2600038,"Settlement failed."),

    NEXMO_NUMBER_CANCEL_REQUEST_PARAM_INVALID(2600039,"Cancellation request's parameters error."),
    NEXMO_NUMBER_CANCEL_REQUEST_FAILED(2600040,"Cancellation failed. Please try again later."),
    NEXMO_NUMBER_CANCEL_REQUEST_NO_RIGHT(2600041,"You have no right to cancel the number."),
    NEXMO_NUMBER_APPLY_NOT_TEAM_CREATOR(2600042,"Only team creator can apply."),
    NEXMO_NUMBER_APPLY_NOT_A_PAID_TEAM(2600043,"Only paid team can apply."),
    VIRTUAL_NUM_SET_CALLRECORD_NO_RIGTH(2600044, "Failed to turn on/off call record. No right."),
    VIRTUAL_NUM_SET_CALLRECORD_USER_NOT_FOUND(2600045, "Failed to turn on/off call record. User not found."),
    NEXMO_NUMBER_APPLY_NUMBER_UPDATE_SETTING_FAILED(2600046,"Update virtual number settings failed."),
    NEXMO_NUMBER_CANCEL_NUMBER_IS_BEING_CONFIGURED(2600047,"This number is being configured."),

    NEXMO_NUMBER_APPLY_NO_CHIME_SUPPORT_RECEIVER_IS_CONFIGURED(2600048,"No Chime support receiver is configured."),
    NEXMO_CHG_PKG_PARAM_INVALID(2600049,"Parameter is invalid."),
    NEXMO_CHG_PKG_NO_AUTH(2600050,"You are not allowed to do it."),
    NEXMO_CHG_PKG_NOT_CHANGE(2600051,"Package does not change."),
    NEXMO_CHG_PKG_NO_VIRTUAL_NUMBER_IS_FOUND(2600052,"No virtual number is found."),
    NEXMO_CHG_PKG_USER_NOT_FOUND(2600052,"User is not found."),


    //2605 账单相关
    NEXMO_BILL_GEN_FAILED_INVALID_PARAMS(2605001,"Invalid parameter."),
    NEXMO_BILL_GEN_FAILED_INVALID_MULTIPLY_FACTOR(2605002,"Invalid multiply factor."),


    //25 questionnaire
    QUESTIONNAIRE_ANSWER_ERROR(2500001, "answer is empty"),
    QUESTIONNAIRE_FROM_JSON_FAILED(250002, "Failed to get questionnaire, please try again"),
    

    
    //27 admin user info
    USER_INFO_NOT_EXIST(2700001, "User info not exist."),
    DELETE_USER_INFO_FAILED(2700002, "Delete user info failed."),
    USER_ACCOUNT_EXISTED(2700003, "User account existed."),
    CREATE_USER_FAILED(2700004, "Create user failed."),
    UPDATE_USER_FAILED(2700005, "Update user failed."),
    SALES_MAN_INVALID(2700006, "Need log in with a sales man account."),
    SALES_AND_USER_NOT_MATCH(2700007, "No authority to operate this user."),
    SALES_NOT_SUPERVISOR(2700008, "Only supervisors have authority to operate."),
    CLIENT_OPERATION_NOT_AUTHORIZED(2700009, "Not authorized."),




    //28 虚拟短信
    VSMS_LEAD_NOT_EXIST(2800000,"Lead does not exist"),
    VSMS_ACCESS_NOT_ALLOWED(2800001,"Access is not allowed"),
    VSMS_PARAM_IS_INVALID(2800003,"Param is invalid"),
    VSMS_VIRTUAL_PHONE_NOT_FOUND(2800004,"Virtual number is not found"),
    VSMS_LEAD_PHONE_NOT_FOUND(2800005,"Lead phone number is not found"),
    VSMS_SEND_FAILED(2800006,"Message send failed"),
    VSMS_VIRTUAL_NUMBER_IS_NOT_ENABLED(2800007,"The text message can't be sent via your virtual number, since it has been disabled in the settings."),
    VSMS_TEXT_IS_BLANK(2800008,"Text cannot be empty"),
    VSMS_TEXT_LENGTH_TOO_LONG(2800009,"The text message is too long."),
    VSMS_TEXT_CHARSET_INVALID(2800010,"The text charset is not supported."),
    VSMS_CHECK_QUOTA_FAILED(2800011, "Sorry, due to carrier restrictions, you can only send up to 470 texts per day."),

    //29 open house

    OPENHOUSE_NOT_EXIST(2900000, "Open house does not exist"),

    //30 lender

    LENDER_USER_IS_NOT_ADMIN(3000001, "Sorry！You're not authorized to operate"),
    LENDER_PARAM_INVALID(3000002, "Lender info invalid."),
    LENDER_COULD_NOT_INVITE(3000003, "This account can't be invited as a lender."),
    LENDER_NOT_BELONG_TO_THIS_TEAM(3000004, "Sorry！You're not authorized to operate."),
    LENDER_UPDATE_FAILED(3000005, "Failed to edit lender info."),
    LENDER_INVITE_SEND_EMAIL_FAILED(3000006, "Failed to send invitation email."),
    LENDER_INVITE_LENDER_MORE_THAN_ONE(3000007, "Sorry! You can only have one lender to share the cost."),
    LENDER_SHOW_ON_WEBSITE_MORE_THAN_ONE(3000008, "Sorry! Only one lender can be shown on the website."),
    LENDER_INVITE_FAILED(3000009, "Failed to add lender."),
    LENDER_PARAM_NAME_INVALID(3000010, "Invalid Name"),
    LENDER_PARAM_PHONE_INVALID(3000011, "Invalid Phone Number"),
    LENDER_PARAM_SLOGAN_INVALID(3000012, "Invalid Info"),
    LENDER_IS_TEAM_MEMBER(3000013, "Sorry! This account has already become an agent"),
    LENDER_EXISTED_AS_OTHER_ROLE(3000014, "Sorry! This account has already become another role"),
    LENDER_EXISTED_AS_LENDER_IN_THE_TEAM(3000015, "Sorry! This account has already been invited."),
    LENDER_INVITE_AGENT_TEAM_NOT_PAID(3000016, "Sorry! You don't have package to pay yet"),
    LENDER_NOT_BELONG_TO_AGENT(3000017, "This lender doesn't belong to you."),
    LENDER_CANNOT_HAS_LEAD_NOT_ACTIVE(3000018, "This lender is inactive."),
    AGENT_NOT_IN_LENDER_INVITATION_WHITELIST(3000019, "Sorry! You're not autorized to invite lender."),
    LENDER_RESEND_INVITATION_LENDER_ACTIVE(3000020, "Lender is already active."),
    LENDER_INVITATION_SHARED_COST_EXISTED_4SHARECOST(3000022, "You have already added a LGCP lender."),
    LENDER_INVITATION_SHARED_COST_EXISTED_4NOCOST(3000023, "You have already added a LGCP lender."),
    LENDER_INVITATION_NOCOST_EXISTED_4SHARECOST(3000024, "Sorry! You can't invite LGCP lender now."),
    LENDER_STATUS_NOT_ACCEPTED_AT_REGISTERING(3000028, "Lender's status is not proper for registeration."),
    LENDER_USER_EXISTED_AT_REGISTER_SUBMMIT(3000029, "Sorry! You can't register again."),
    LENDER_UPDATE_STATUS_AFTER_REGISTER_FAILED(3000030, "Failed to update lender status."),
    LENDER_DELETE_FAILED(3000031, "Failed to delete lender."),
    LENDER_RESEND_INVITATION_LENDER_STATUS_ERROR(3000032, "Lender had accepted or declined and cannot be invited again."),
    LENDER_RESEND_INVITATION_LENDER_NOT_EXIST(3000033, "Lender had been deleted or not exist."),
    LENDER_DELETE_LENDER_NOT_EXIST(3000034, "Lender is already deleted or not exist."),
    LENDER_REGISTER_ACCOUNT_REGISTERED(3000036, "Sorry! Your email has already been registered with."),
    LENDER_REGISTER_LENDER_STATUS_NOT_INVITED(3000038, "Sorry! You have already become an active lender."),
    LENDER_DECLINE_LENDER_DELETED(3000040, "Sorry! The agent admin has changed your stage in Chime"),
    LENDER_DECLINE_LENDER_ACCEPTED(3000041, "Sorry! You have already accepted this invitation."),
    LENDER_DECLINE_SUCCESS(3000044, "Declined Successfully!"),
    LENDER_CANNOT_SHOW_ON_WEBSITE(3000045, "Sorry! You can't turn on \" show on website\"."),
    LENDER_SHARECOST_CANNOT_TURN_OFF_SENDLEADS(3000046, "Sorry! You can't turn off \"send leads\" ."),
    USER_TO_OPERATE_LENDER_IS_NOT_CREATOR(3000047, "Sorry! Only creator can operate this."),
    LEAD_CANNOT_ASSIGN_LENDER_BY_SETTING_AT_ADDING(3000048, "Sorry!  This lead refuse to share info with any lenders."),
    LEAD_IS_OWNED_BY_COST_LENDER(3000049, "Sorry! This lead is bought and owned by a co-marketing lender."),
    LENDER_INVITATION_NOT_FOUND(3000050,"Sorry! Your agent partner has made a new operation on your identity."),
    LENDER_INVITATION_NOT_IN_USE(3000051,"Sorry! Your agent partner has made a new operation on your identity."),
    LENDER_INVITATION_OVERDUE(3000052,"Sorry! The invitation has expired."),
    LENDER_ACCEPT_LENDER_DELETED(3000053, "Sorry! The agent admin has changed your stage in Chime."),
    LENDER_ACCEPT_EXCEPTIONS(3000054, "Sorry! There is a system error."),
    LENDER_PAY_TO_TERMINATE_CANNOT_UPDATE(3000056, "Sorry! You have already deleted this lender, (s)he will disappear at the end of this billing cycle."),

    //31
    ZAPIER_AUTH_FAILED(3100000,"Auth Failed"),
    ZAPIER_INVALID_API_KEY(3100001,"Invalid api key"),
    ZAPIER_EMPTY_API_KEY(3100002,"Api key is missing"),
    ZAPIER_USER_NOT_FOUND(3100003,"User is not found"),
    ZAPIER_PARAM_INVALID(3100004,"Parameter is invalid"),
    ZAPIER_SUBSCRIPTION_INFO_IS_NULL(3100005,"Zapier subscription info is null"),
    ZAPIER_UNSUBSCRIPTION_INFO_IS_NULL(3100006,"Zapier unSubscription info is null"),
    ZAPIER_SUBSCRIBE_FAILED(3100007,"Subscribe failed"),
    ZAPIER_UNSUBSCRIBE_FAILED(3100008,"Unsubscribe failed"),
    ZAPIER_NOTIFY_NEW_LEAD_FAILED(3100009,"Notifying new lead failed"),
    ZAPIER_DIGEST_NEW_LEAD_FAILED(3100010,"Digesting new lead failed"),
    ZAPIER_GENERATE_API_KEY_FAILED(3100011,"Generating api key failed"),
    ZAPIER_NOTIFY_LEAD_GROUP_CHANGE_FAILED(3100012,"Notifying lead group change failed"),

    //32 lead document
    LEAD_DOCUMENT_TO_DELETE_NOT_EXIST(3200001, "Document to delete does not exist."),
    LEAD_DOCUMENT_TO_DELETE_NOT_BELONG_TO_USER(3200002, "Document to delete doesn't belong to you."),
    LEAD_DOCUMENT_DELETE_FAILED(3200003, "Failed to delete document."),
    LEAD_DOCUMENT_ADD_NO_DOCNAME(3200004, "It lacks document name for uploading."),
    LEAD_DOCUMENT_ADD_FAILED(3200005, "Failed to upload document."),
    LEAD_DOCUMENT_ADD_FROM_GOOGLEDRIVE_DOWNLOAD_FAILED(3200006, "Failed to download document from googledrive."),
    LEAD_DOCUMENT_ADD_FROM_DROPBOX_DOWNLOAD_FAILED(3200007, "Failed to download document from dropbox."),

    //33 empty state
    EMPTY_STATE_IS_EMPTY(3300000,"Empty state info is empty"),
    EMPTY_STATE_PARAM_INVALID(3300001,"Empty state param is invalid"),
    EMPTY_STATE_EMAIL_RECEIVER_NOT_SPECIFIED(3300002,"Email receivers are not specified"),


    
    //34 brokergaeTeam
    BROKERAGE_TEAM_DELETE_FAILED(3400000,"Failed to delete brokerageTeam."),
    BROKERAGE_INVITE_AGENT_EMAIL_EXIST(3400002, "This agent has already been invited."),
    BROKERAGE_INVITE_AGENT_EMAIL_TWICE(3400002, "Sorry! This agent is currently occupied with another team."),
    
    //35 brokerage billing
    NO_BILLING_INTENTION(3500000,"user has chose no upgrade intention"),
    EMAILSERVICE_ERROE(3500001,"Email service has error"),

    //36 exchange
    EXCHANGE_GET_SERVICE_FAIL(3600000, "Authorization failed, please enter your server url."),
    EXCHANGE_NOT_AUTH_YET(3600001, "You haven't auth exchange in chime yet."),
    EXCHANGE_ACCOUNT_CANNOT_EMPTY(3600002, "Account cannot be empty."),
    EXCHANGE_PASSWORD_CANNOT_EMPTY(3600003, "Password cannot be empty."),
    EXCHANGE_ACCOUNT_EXIST_YET(3600004, "The Exchange account you entered has already been connected to another user."),
    MESSAGE_NOT_EXIST(3600005, "Exhange Message Not Found."),

    //37 IP restrict
    IP_RESTRICT(3700000, "Your IP had been restricted."),

    //User tag
    USER_TAG_NOT_EXIST(38000001, "Tag does not exist"),
    USER_TAG_ALREADY_EXIST(38000002, "Tag has already been taken"),

    //39 Phone, Email check
    ILLEGAL_LENGTH(3900000, "Illegal length."),
    
    //40 location search
    LOCATION_SEARCH_FIELD_ERROR(4000000,"the search field is error"),

    //41 brokermint
    BROKERMINT_APIKEY_IS_EMPTY(4100000,"Api Key is empty"),
    BROKERMINT_TRANSFERRED_FAILED(4100001,"Transferred failed"),
    BROKERMINT_NOT_CONNECTED(4100002,"Please connect Brokermint first"),
    BROKERMINT_LEAD_NOT_FOUND(4100003,"Lead not found"),
    BROKERMINT_LEAD_BASIC_INFO_NOT_FOUND(4100004,"Lead basic info not found"),
    BROKERMINT_SERVER_ERROR(4100005,"Brokermint server error"),
    BROKERMINT_CONNECT_FAILED(4100006,"Connecting to Brokermint failed"),
    BROKERMINT_INVALID_API_KEY(4100007,"Connecting to Brokermint failed, please check the API key provided."),
    BROKERMINT_CONTACT_NOT_EXIST(4100008,"Brokermint contact info does not exist"),

    
    //42 timeline
    LEAD_TIMELINE_NOT_EXIST(4200000,"The timeline not exist"),

    //43 API Token Authorization
    API_TOKEN_AUTHORIZATION_HEADER_ERROR(4300001, "The Authorization Header Not Exist or Wrong Fromart"),
    API_TOKEN_INVALID(4300002, "The Api Token Provided is Invalid"),

    // 44 AgentLead
    AGENT_LEAD_REGISTER_OR_UPDATE_ERROR(4400002, "Register or Update Agent Lead Error. "),

    // 45 Lead Client Tracking
    LEAD_CLIENT_TRACKING_ADD_FAILED(4500002, "Lead Client Tracking Add Failed. "),

    //46 reminder
    CHAT_REMINDER_NOT_EXIST(4600000, "chatReminder does not exist"),
    CHAT_REMINDER_FROM_USER_NOT_EXIST(4600001, "chatReminder's fromUser does not exist"),
    CHAT_REMINDER_TO_USER_NOT_EXIST(4600002, "chatReminder's toUser does not exist"),
    CHAT_REMINDER_LEAD_NOT_EXIST(4600003, "chatReminder's lead does not exist"),

    //47 nim
    NIM_TOKEN_REFRESH_FAIL(4700000, "Refresh Neatease Token Fail"),
    NIM_TOKEN_NOT_EXIST(4700001,"NimToken not exist"),
    NIM_ACCID_DOES_NOT_EXIST(4700002, "Nim accid does not exist"),
    NIM_UPDATE_INFO_ERROR(4700003, "Update Nim Info Error."),

    //48 bombbomb
    BOMBBOMB_CONNECT_PARAM_INVALID(4800000, "Param invalid."),
    BOMBBOMB_CONNECT_USER_NOT_AGENT(4800001, "User is not agent."),
    BOMBBOMB_CONNECT_USER_CONNECTED(4800002, "Sorry！You have already connected BombBomb."),
    BOMBBOMB_CONNECT_FAILED(4800003, "Failed to connect."),
    BOMBBOMB_DISCONNECT_PARAM_INVALID(4800004, "Invalid param."),
    BOMBBOMB_DISCONNECT_NOT_CONNECTED(4800005, "Sorry！You haven't connected BombBomb yet."),
    BOMBBOMB_DISCONNECT_FAILED(4800006, "Failed to disconnect ."),
    BOMBBOMB_GET_APIKEY_PARAM_INVALID(4800007, " Invalid param."),

    
    //1.5.4 welcome email
    WELCOME_EMAIL_NOT_EXIST(4900000, "welcome email not exist"),
    
    NO_RIGHT_TO_DO(5000001, "You have no right to %s %s."),
    ALREADY_EXIST(5000002, "%s '%s' already exists."),
    Object_NOT_EXIST(5000003, "%s does not exist"),
    DATA_TOO_LONG(5000004, "Data too long for %s."),
    DATA_DELETED(5000005, "%s has been deleted"),
    CONNOT_DELETE_SOURCE(5000006, "Can't delete the source"),

    // 1.5.5

    LEAD_DRIP_MAIL_TERMINATED(5100001, "your dripmail terminated"),

    EMAIL_SUBSCRIBE_KEY_INVALID(5200002, "the subscribe key is invalid"),

    PARAMETER_INVALID(5200003, "Parameter is invalid"),

    // 1.5.8
    CANNOT_OPERATE_FINAL_STAGE(5200004, "You cannot operate final stage"),

    GROUP_TEXT_ADD_ERROR(5300001, "Error occurs when adding group text."),
    GROUP_TEXT_PARAM_INVALID(5300002, "Param invalid."),
    GROUP_TEXT_NO_VIRTUALNUMBER(5300003, "No virtual number."),
    GROUP_TEXT_LEADS_EXCEED_MAX_LIMIT(5300004, "Too much leads."),
    GROUP_TEXT_SMS_QUOTA_NOT_ENOUGH(5300005, "SMS quota insufficient."),
    GROUP_TEXT_SOME_LEAD_CANNOT_GET(5300006, "The mass texts service will be terminated for some leads are deleted or cannot be acquired."),
    GROUP_TEXT_SOME_LEAD_NOT_BELONG_TEAM(5300007, "The mass texts service will be terminated for some leads are not belong to your team."),
    GROUP_TEXT_SOME_LEAD_NO_PHONE(5300008, "%s %s not have a phone number."),
    GROUP_TEXT_SOME_LEAD_NO_PHONE_TO_TERMINATE_ONE_LEAD(5300009, "The mass texts service will be terminated for %s does not have a phone number."),
    GROUP_TEXT_SOME_LEAD_NO_PHONE_TO_TERMINATE_SOME_LEAD(5300010, "The mass texts service will be terminated for some selected leads do not have phone numbers."),


    //App Version Info
    APP_VERSION_INVALID_VERSION_FORMAT(5400001, "Invalid Version Format"),
    APP_VERSION_INVALID_UPDATE_TYPE(5400002, "Invalid Update Type"),
    APP_VERSION_NULL_MARKET_URL(5400003, "Null Market Url"),
    APP_VERSION_NULL_TITLE(5400004, "Null Title"),
    APP_VERSION_NULL_CONTENT(5400005, "Null Content"),
    APP_VERSION_INVALID_STATUS(5400006, "Invalid Status"),
    APP_VERSION_LOWER_VERSION(5400007, "Lower Version String"),

    // 1.7.0 Free Trial
    VERIFICATION_CODE_IS_WRONG(5500001, "The verification code is wrong."),
    VERIFICATION_CODE_OVER_THE_LIMIT(5500002, "Sorry, we cannot send more than 10 verification codes per day."),
    SEND_VERIFICATION_CODE_ERROR(5500003, "Send verification code error."),
    EMAIL_ALREADY_REG_FT(5500004, "You are one of our customers and do not need to upgrade. If you have questions, please contact our support team: info@chime.me"),
    
    GROUP_ACTIONPLAN_OVER_LIMIT(5500005,"Please wait until the previous setting is applied"),

    TEAM_SOURCE_MUST_HAVE_A_SHOW_SOURCE(5600001, "At lead one source must remain"),
    TEAM_SOURCE_CANNOT_EDIT_HIDE(5600002, "You cannot edit this source's hide status"),
    ;

  private int errorCode;

  /**
   */
  private String errorMsg;

  private static Map<Integer, String> codeAndMsgMap = new HashMap<Integer, String>();

  private ErrorCodeEnum(int errorCode, String errorMsg) {
    this.errorCode = errorCode;
    this.errorMsg = errorMsg;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  public static ErrorCodeEnum getByCode(int errorCode) {
    for (ErrorCodeEnum errorCodeEnum : ErrorCodeEnum.values()) {
      if (errorCodeEnum.getErrorCode() == errorCode) {
        return errorCodeEnum;
      }
    }
    return null;
  }

  public static Map<Integer, String> getCodeAndMsgMap() {
    if (codeAndMsgMap.keySet().size() == 0) {
      for (ErrorCodeEnum ece : ErrorCodeEnum.values()) {
        codeAndMsgMap.put(ece.getErrorCode(), ece.getErrorMsg());
      }
    }
    return codeAndMsgMap;
  }
}

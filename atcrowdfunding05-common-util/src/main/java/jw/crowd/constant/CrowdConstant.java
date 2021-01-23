package jw.crowd.constant;

public class CrowdConstant {
    public static final String ATTR_NAME_EXCEPTION = "exception";
    public static final String ATTR_NAME_LOGIN_ADMIN = "loginAdmin";
    public static final String ATTR_NAME_LOGIN_MEMBER = "loginMember";
    public static final String ATTR_NAME_PAGE_INFO = "pageInfo";
    public static final String ATTR_NAME_MESSAGE = "message";
    public static final String ATTR_NAME_TEMP_PROJECT = "tempProject";

    public static final String MESSAGE_LOGIN_FAILED = "登录失败！请确认账号密码是否正确！";
    public static final String MESSAGE_STRING_INVALIDATE = "字符串不合法，请不要传入空串！";
    public static final String MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE = "系统错误，登录账号不唯一！";
    public static final String MESSAGE_ACCESS_FORBIDEN = "请登录后再访问！";
    public static final String MESSAGE_LOGIN_ACCT_ALREADY_IN_USE = "该账户已被注册！";
    public static final String MESSAGE_ACCESS_DENIED = "无访问权限";
    public static final String MESSAGE_CODE_NOT_EXISTS = "验证码已过期！请检查手机号是否正确或重新发送！";
    public static final String MESSAGE_CODE_INVALID = "验证码不正确！";
    public static final String REDIS_CODE_PREFIX = "REDIS_CODE_PREFIX_";
    public static final Object MESSAGE_HEADER_PIC_EMPTY = "头图不能为空！";
    public static final Object MESSAGE_HEADER_PIC_UPLOAD_FAILED = "头图上传失败！";
    public static final Object MESSAGE_DETAIL_PIC_EMPTY = "详情图为空！";
    public static final Object MESSAGE_DETAIL_PIC_UPLOAD_FAILED = "详情图上传失败！";

    public static final String MESSAGE_TEMP_PROJECT_MISSING = "临时存储的project对象丢失！";
}

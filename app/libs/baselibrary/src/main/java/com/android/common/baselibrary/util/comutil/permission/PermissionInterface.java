package com.android.common.baselibrary.util.comutil.permission;

import java.util.List;

public interface PermissionInterface {
    /**
     * 可设置请求权限码
     * @return
     */
    int getPermissonRequestCode();

    /**
     * 设置需要申请的权限
     * @return
     */
    String[] getPermissions();

    /**
     * 请求权限成功回调
     */
    void requestPermissionsSuccess(List<String> grantedPermissionsList);

    /**
     * 请求权限失败的回调
     */
    void requestPermissionsFail(List<String> permissionList);

    /**
     * 自定义的权限解释对话框全部消失之后的回调
     */
    void allDialogDismiss();

}

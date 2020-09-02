package com.android.common.baselibrary.util.comutil.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;


import com.android.common.baselibrary.util.PermissionUtil;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 动态权限帮助类
 */
public class PermissionHelper {
    public Activity mActivity;
    private PermissionInterface mPermissionInterface;
    private List<String> deniedPermissionList = new ArrayList<>();
    private List<String> grantedPermissionList = new ArrayList<>();

    public PermissionHelper(@NonNull Activity activity, @NonNull PermissionInterface permissionInterface) {
        this.mActivity = activity;
        this.mPermissionInterface = permissionInterface;
    }

    /**
     * 开始请求权限
     */
    public void requestPermissions(String[] permissionsArray) {
       // String[] requestPermissionArray = mPermissionInterface.getPermissions();
        String[] denidePermissions = PermissionUtil.getDeniedPermissions(mActivity, permissionsArray);
        if (null != denidePermissions && denidePermissions.length > 0) {
            PermissionUtil.requestPermissons(mActivity, denidePermissions, mPermissionInterface.getPermissonRequestCode());
        } else {
            mPermissionInterface.requestPermissionsSuccess(Arrays.asList(permissionsArray));
        }
    }

    public boolean requestPermissonsResult(int requestCode, @NonNull String[] permissons, @NonNull int[] grantResults) {
        if (requestCode == mPermissionInterface.getPermissonRequestCode() ) {
            deniedPermissionList.clear();
            grantedPermissionList.clear();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    deniedPermissionList.add(permissons[i]);
                } else if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    grantedPermissionList.add(permissons[i]);
                }
            }

            mPermissionInterface.requestPermissionsSuccess(grantedPermissionList);
            mPermissionInterface.requestPermissionsFail(deniedPermissionList);
            return true;
        }
        return false;
    }

}

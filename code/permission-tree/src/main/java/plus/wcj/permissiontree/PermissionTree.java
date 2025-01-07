package plus.wcj.permissiontree;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author changjin wei(魏昌进)
 * @since 2023/6/2
 */
public class PermissionTree {

    private String permission;

    private Set<PermissionTree> children;


    public PermissionTree(String permission) {
        this.permission = permission;
        this.children = new HashSet<>();
    }

    public Set<PermissionTree> getChildren() {
        return children;
    }

    public void setChildren(Set<PermissionTree> children) {
        this.children = children;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PermissionTree that)) return false;
        return Objects.equals(getPermission(), that.getPermission()) && Objects.equals(getChildren(), that.getChildren());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPermission(), getChildren());
    }
}

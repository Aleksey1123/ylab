package org.example.model;

import javax.validation.constraints.NotBlank;

public class UserDTO {
    private int id;
    private @NotBlank(
            message = "Provide the username"
    ) String username;
    private @NotBlank(
            message = "Provide a password"
    ) String password;

    public static UserDTOBuilder builder() {
        return new UserDTOBuilder();
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof UserDTO)) {
            return false;
        } else {
            UserDTO other = (UserDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getId() != other.getId()) {
                return false;
            } else {
                Object this$username = this.getUsername();
                Object other$username = other.getUsername();
                if (this$username == null) {
                    if (other$username != null) {
                        return false;
                    }
                } else if (!this$username.equals(other$username)) {
                    return false;
                }

                Object this$password = this.getPassword();
                Object other$password = other.getPassword();
                if (this$password == null) {
                    if (other$password != null) {
                        return false;
                    }
                } else if (!this$password.equals(other$password)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof UserDTO;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + this.getId();
        Object $username = this.getUsername();
        result = result * 59 + ($username == null ? 43 : $username.hashCode());
        Object $password = this.getPassword();
        result = result * 59 + ($password == null ? 43 : $password.hashCode());
        return result;
    }

    public String toString() {
        int var10000 = this.getId();
        return "UserDTO(id=" + var10000 + ", username=" + this.getUsername() + ", password=" + this.getPassword() + ")";
    }

    public UserDTO() {
    }

    public UserDTO(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static class UserDTOBuilder {
        private int id;
        private String username;
        private String password;

        UserDTOBuilder() {
        }

        public UserDTOBuilder id(int id) {
            this.id = id;
            return this;
        }

        public UserDTOBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserDTOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(this.id, this.username, this.password);
        }

        public String toString() {
            return "UserDTO.UserDTOBuilder(id=" + this.id + ", username=" + this.username + ", password=" + this.password + ")";
        }
    }
}

package by.jwd.cafe.model.entity;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.StringJoiner;

/**
 * {@code User} class
 * is an entity that represents table 'users' in the database.
 */
public class User extends AbstractEntity {
    @Serial
    private static final long serialVersionUID = 1L;
    private int userId;
    private String email;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private BigDecimal balance;
    private BigDecimal loyaltyPoints;
    private boolean active;
    private UserRole role;


    public User() {
    }

    public static class UserBuilder {
        private User newUser = new User();

        public UserBuilder() {

        }

        public UserBuilder withUserId(int userId) {
            newUser.userId = userId;
            return this;
        }

        public UserBuilder withLogin(String login) {
            newUser.login = login;
            return this;
        }

        public UserBuilder withEmail(String email) {
            newUser.email = email;
            return this;
        }

        public UserBuilder withPassword(String password) {
            newUser.password = password;
            return this;
        }

        public UserBuilder withFirstName(String firstName) {
            newUser.firstName = firstName;
            return this;
        }

        public UserBuilder withLastName(String lastName) {
            newUser.lastName = lastName;
            return this;
        }

        public UserBuilder withBalance(BigDecimal balance) {
            newUser.balance = balance;
            return this;
        }

        public UserBuilder withLoyaltyPoints(BigDecimal loyaltyPoints) {
            newUser.loyaltyPoints = loyaltyPoints;
            return this;
        }

        public UserBuilder withIsActive(boolean isActive) {
            newUser.active = isActive;
            return this;
        }

        public UserBuilder withUserRoleById(int roleId) {
            switch (roleId) {
                case 1:
                    newUser.role = UserRole.CUSTOMER;
                    break;
                case 2:
                    newUser.role = UserRole.ADMIN;
                    break;
                default:
                    newUser.role = UserRole.UNSUPPORTED;
            }
            return this;
        }

        public UserBuilder withUserRole(UserRole role) {
            newUser.role = role;
            return this;
        }

        public User build() {
            return newUser;
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(BigDecimal loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(int roleId) {
        switch (roleId) {
            case 1:
                this.role = UserRole.CUSTOMER;
                break;
            case 2:
                this.role = UserRole.ADMIN;
                break;
            default:
                this.role = UserRole.UNSUPPORTED;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (active != user.active) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (balance != null ? !balance.equals(user.balance) : user.balance != null) return false;
        if (loyaltyPoints != null ? !loyaltyPoints.equals(user.loyaltyPoints) : user.loyaltyPoints != null)
            return false;
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (loyaltyPoints != null ? loyaltyPoints.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "User{", "}");
        joiner.add("userId=" + userId);
        joiner.add("login=" + login);
        joiner.add("email=" + email);
        joiner.add("password=" + password);
        joiner.add("firstName=" + firstName);
        joiner.add("lastName=" + lastName);
        joiner.add("balance=" + balance);
        joiner.add("loyaltyPoints=" + loyaltyPoints);
        joiner.add("active=" + active);
        joiner.add("role=" + role);
        return joiner.toString();
    }
}

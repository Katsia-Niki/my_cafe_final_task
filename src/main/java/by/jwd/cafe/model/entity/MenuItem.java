package by.jwd.cafe.model.entity;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * {@code MenuItem} class represent a menu item
 *
 * @see AbstractEntity
 */
public class MenuItem extends AbstractEntity {
    @Serial
    private static final long serialVersionUID = 1L;
    private int menuItemId;
    private MenuItemType menuItemType;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean available;
    private byte[] picture;
    private String image;

    public MenuItem() {
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public MenuItemType getMenuItemType() {
        return menuItemType;
    }

    public void setMenuItemType(int menuItemTypeId) {
        switch (menuItemTypeId) {
            case 1 -> this.menuItemType = MenuItemType.APPETIZER;
            case 2 -> this.menuItemType = MenuItemType.MAIN_COURSE;
            case 3 -> this.menuItemType = MenuItemType.SOUP;
            case 4 -> this.menuItemType = MenuItemType.DESSERT;
            case 5 -> this.menuItemType = MenuItemType.DRINK;
            default -> this.menuItemType = MenuItemType.UNSUPPORTED;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static class MenuItemBuilder {
        private MenuItem newMenuItem = new MenuItem();

        public MenuItemBuilder withMenuItemId(int menuItemId) {
            newMenuItem.menuItemId = menuItemId;
            return this;
        }

        public MenuItemBuilder withMenuItemTypeById(int menuItemTypeId) {
            switch (menuItemTypeId) {
                case 1:
                    newMenuItem.menuItemType = MenuItemType.APPETIZER;
                    break;
                case 2:
                    newMenuItem.menuItemType = MenuItemType.MAIN_COURSE;
                    break;
                case 3:
                    newMenuItem.menuItemType = MenuItemType.SOUP;
                    break;
                case 4:
                    newMenuItem.menuItemType = MenuItemType.DESSERT;
                    break;
                case 5:
                    newMenuItem.menuItemType = MenuItemType.DRINK;
                    break;
                default:
                    newMenuItem.menuItemType = MenuItemType.UNSUPPORTED;
            }
            return this;
        }

        public MenuItemBuilder withMenuItemType(MenuItemType menuItemType) {
            newMenuItem.menuItemType = menuItemType;
            return this;
        }

        public MenuItemBuilder withName(String name) {
            newMenuItem.name = name;
            return this;
        }

        public MenuItemBuilder withDescription(String description) {
            newMenuItem.description = description;
            return this;
        }

        public MenuItemBuilder withPrice(BigDecimal price) {
            newMenuItem.price = price;
            return this;
        }

        public MenuItemBuilder withIsAvailable(boolean isAvailable) {
            newMenuItem.available = isAvailable;
            return this;
        }

        public MenuItemBuilder withImage(String image) {
            newMenuItem.image = image;
            return this;
        }

        public MenuItemBuilder withPicture(byte[] picture) {
            newMenuItem.picture = picture;
            return this;
        }

        public MenuItem build() {
            return newMenuItem;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuItem menuItem = (MenuItem) o;

        if (menuItemId != menuItem.menuItemId) return false;
        if (available != menuItem.available) return false;
        if (menuItemType != menuItem.menuItemType) return false;
        if (name != null ? !name.equals(menuItem.name) : menuItem.name != null) return false;
        if (description != null ? !description.equals(menuItem.description) : menuItem.description != null)
            return false;
        if (price != null ? !price.equals(menuItem.price) : menuItem.price != null) return false;
        return Arrays.equals(picture, menuItem.picture);
    }

    @Override
    public int hashCode() {
        int result = menuItemId;
        result = 31 * result + (menuItemType != null ? menuItemType.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (available ? 1 : 0);
        result = 31 * result + Arrays.hashCode(picture);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MenuItem{");
        sb.append("menuItemId=").append(menuItemId);
        sb.append(", menuItemType=").append(menuItemType);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", price=").append(price);
        sb.append(", isAvailable=").append(available);
        sb.append('}');
        return sb.toString();
    }
}

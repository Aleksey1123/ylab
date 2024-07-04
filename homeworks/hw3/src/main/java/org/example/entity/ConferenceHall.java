package org.example.entity;

public class ConferenceHall {
    private Integer id;
    private String description;
    private Integer size;

    public static ConferenceHallBuilder builder() {
        return new ConferenceHallBuilder();
    }

    public Integer getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ConferenceHall)) {
            return false;
        } else {
            ConferenceHall other = (ConferenceHall)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label47;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label47;
                    }

                    return false;
                }

                Object this$size = this.getSize();
                Object other$size = other.getSize();
                if (this$size == null) {
                    if (other$size != null) {
                        return false;
                    }
                } else if (!this$size.equals(other$size)) {
                    return false;
                }

                Object this$description = this.getDescription();
                Object other$description = other.getDescription();
                if (this$description == null) {
                    if (other$description != null) {
                        return false;
                    }
                } else if (!this$description.equals(other$description)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ConferenceHall;
    }

    public int hashCode() {
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $size = this.getSize();
        result = result * 59 + ($size == null ? 43 : $size.hashCode());
        Object $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        return result;
    }

    public String toString() {
        Integer var10000 = this.getId();
        return "ConferenceHall(id=" + var10000 + ", description=" + this.getDescription() + ", size=" + this.getSize() + ")";
    }

    public ConferenceHall() {
    }

    public ConferenceHall(Integer id, String description, Integer size) {
        this.id = id;
        this.description = description;
        this.size = size;
    }

    public static class ConferenceHallBuilder {
        private Integer id;
        private String description;
        private Integer size;

        ConferenceHallBuilder() {
        }

        public ConferenceHallBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public ConferenceHallBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ConferenceHallBuilder size(Integer size) {
            this.size = size;
            return this;
        }

        public ConferenceHall build() {
            return new ConferenceHall(this.id, this.description, this.size);
        }

        public String toString() {
            return "ConferenceHall.ConferenceHallBuilder(id=" + this.id + ", description=" + this.description + ", size=" + this.size + ")";
        }
    }
}

package org.example.entity;

/** Entity class for storing and creating new workplaces. **/
public class Workplace {
    private Integer id;
    private String description;

    public static WorkplaceBuilder builder() {
        return new WorkplaceBuilder();
    }

    public Integer getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Workplace)) {
            return false;
        } else {
            Workplace other = (Workplace)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$id = this.getId();
                Object other$id = other.getId();
                if (this$id == null) {
                    if (other$id != null) {
                        return false;
                    }
                } else if (!this$id.equals(other$id)) {
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
        return other instanceof Workplace;
    }

    public int hashCode() {
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        return result;
    }

    public String toString() {
        Integer var10000 = this.getId();
        return "Workplace(id=" + var10000 + ", description=" + this.getDescription() + ")";
    }

    public Workplace() {
    }

    public Workplace(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public static class WorkplaceBuilder {
        private Integer id;
        private String description;

        WorkplaceBuilder() {
        }

        public WorkplaceBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public WorkplaceBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Workplace build() {
            return new Workplace(this.id, this.description);
        }

        public String toString() {
            return "Workplace.WorkplaceBuilder(id=" + this.id + ", description=" + this.description + ")";
        }
    }
}

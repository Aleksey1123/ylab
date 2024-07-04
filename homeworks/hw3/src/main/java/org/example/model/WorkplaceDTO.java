package org.example.model;

import javax.validation.constraints.NotBlank;

public class WorkplaceDTO {
    private Integer id;
    private @NotBlank(
            message = "Provide a description"
    ) String description;

    public @NotBlank(
            message = "Provide a description"
    ) String getDescription() {
        return this.description;
    }

    public void setDescription(@NotBlank(
            message = "Provide a description"
    ) String description) {
        this.description = description;
    }

    public static WorkplaceDTOBuilder builder() {
        return new WorkplaceDTOBuilder();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WorkplaceDTO)) {
            return false;
        } else {
            WorkplaceDTO other = (WorkplaceDTO)o;
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
        return other instanceof WorkplaceDTO;
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
        return "WorkplaceDTO(id=" + var10000 + ", description=" + this.getDescription() + ")";
    }

    public WorkplaceDTO() {
    }

    public WorkplaceDTO(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public static class WorkplaceDTOBuilder {
        private Integer id;
        private String description;

        WorkplaceDTOBuilder() {
        }

        public WorkplaceDTOBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public WorkplaceDTOBuilder description(String description) {
            this.description = description;
            return this;
        }

        public WorkplaceDTO build() {
            return new WorkplaceDTO(this.id, this.description);
        }

        public String toString() {
            return "WorkplaceDTO.WorkplaceDTOBuilder(id=" + this.id + ", description=" + this.description + ")";
        }
    }
}

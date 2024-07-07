package org.example.model;

import javax.validation.constraints.NotBlank;

/** DTO class for user interactions with the conferenceHall entity. **/
public class ConferenceHallDTO {
    private Integer id;
    private @NotBlank(
            message = "Provide a description"
    ) String description;
    private @NotBlank(
            message = "Provide a size"
    ) Integer size;

    public static ConferenceHallDTOBuilder builder() {
        return new ConferenceHallDTOBuilder();
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
        } else if (!(o instanceof ConferenceHallDTO)) {
            return false;
        } else {
            ConferenceHallDTO other = (ConferenceHallDTO)o;
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
        return other instanceof ConferenceHallDTO;
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
        return "ConferenceHallDTO(id=" + var10000 + ", description=" + this.getDescription() + ", size=" + this.getSize() + ")";
    }

    public ConferenceHallDTO() {
    }

    public ConferenceHallDTO(Integer id, String description, Integer size) {
        this.id = id;
        this.description = description;
        this.size = size;
    }

    public static class ConferenceHallDTOBuilder {
        private Integer id;
        private String description;
        private Integer size;

        ConferenceHallDTOBuilder() {
        }

        public ConferenceHallDTOBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public ConferenceHallDTOBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ConferenceHallDTOBuilder size(Integer size) {
            this.size = size;
            return this;
        }

        public ConferenceHallDTO build() {
            return new ConferenceHallDTO(this.id, this.description, this.size);
        }

        public String toString() {
            return "ConferenceHallDTO.ConferenceHallDTOBuilder(id=" + this.id + ", description=" + this.description + ", size=" + this.size + ")";
        }
    }
}

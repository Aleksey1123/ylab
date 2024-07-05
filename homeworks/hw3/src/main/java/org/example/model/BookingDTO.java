package org.example.model;

import org.example.entity.User;

import java.time.LocalDateTime;

public class BookingDTO {
    private Integer id;
    private Integer workplaceId;
    private Integer hallId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private User user;

    public static BookingDTOBuilder builder() {
        return new BookingDTOBuilder();
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getWorkplaceId() {
        return this.workplaceId;
    }

    public Integer getHallId() {
        return this.hallId;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public User getUser() {
        return this.user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setWorkplaceId(Integer workplaceId) {
        this.workplaceId = workplaceId;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BookingDTO)) {
            return false;
        } else {
            BookingDTO other = (BookingDTO)o;
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

                Object this$workplaceId = this.getWorkplaceId();
                Object other$workplaceId = other.getWorkplaceId();
                if (this$workplaceId == null) {
                    if (other$workplaceId != null) {
                        return false;
                    }
                } else if (!this$workplaceId.equals(other$workplaceId)) {
                    return false;
                }

                Object this$hallId = this.getHallId();
                Object other$hallId = other.getHallId();
                if (this$hallId == null) {
                    if (other$hallId != null) {
                        return false;
                    }
                } else if (!this$hallId.equals(other$hallId)) {
                    return false;
                }

                label62: {
                    Object this$startTime = this.getStartTime();
                    Object other$startTime = other.getStartTime();
                    if (this$startTime == null) {
                        if (other$startTime == null) {
                            break label62;
                        }
                    } else if (this$startTime.equals(other$startTime)) {
                        break label62;
                    }

                    return false;
                }

                label55: {
                    Object this$endTime = this.getEndTime();
                    Object other$endTime = other.getEndTime();
                    if (this$endTime == null) {
                        if (other$endTime == null) {
                            break label55;
                        }
                    } else if (this$endTime.equals(other$endTime)) {
                        break label55;
                    }

                    return false;
                }

                Object this$user = this.getUser();
                Object other$user = other.getUser();
                if (this$user == null) {
                    if (other$user != null) {
                        return false;
                    }
                } else if (!this$user.equals(other$user)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof BookingDTO;
    }

    public int hashCode() {
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $workplaceId = this.getWorkplaceId();
        result = result * 59 + ($workplaceId == null ? 43 : $workplaceId.hashCode());
        Object $hallId = this.getHallId();
        result = result * 59 + ($hallId == null ? 43 : $hallId.hashCode());
        Object $startTime = this.getStartTime();
        result = result * 59 + ($startTime == null ? 43 : $startTime.hashCode());
        Object $endTime = this.getEndTime();
        result = result * 59 + ($endTime == null ? 43 : $endTime.hashCode());
        Object $user = this.getUser();
        result = result * 59 + ($user == null ? 43 : $user.hashCode());
        return result;
    }

    public String toString() {
        Integer var10000 = this.getId();
        return "BookingDTO(id=" + var10000 + ", workplaceId=" + this.getWorkplaceId() + ", hallId=" + this.getHallId() + ", startTime=" + this.getStartTime() + ", endTime=" + this.getEndTime() + ", user=" + this.getUser() + ")";
    }

    public BookingDTO() {
    }

    public BookingDTO(Integer id, Integer workplaceId, Integer hallId, LocalDateTime startTime, LocalDateTime endTime, User user) {
        this.id = id;
        this.workplaceId = workplaceId;
        this.hallId = hallId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
    }

    public static class BookingDTOBuilder {
        private Integer id;
        private Integer workplaceId;
        private Integer hallId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private User user;

        BookingDTOBuilder() {
        }

        public BookingDTOBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public BookingDTOBuilder workplaceId(Integer workplaceId) {
            this.workplaceId = workplaceId;
            return this;
        }

        public BookingDTOBuilder hallId(Integer hallId) {
            this.hallId = hallId;
            return this;
        }

        public BookingDTOBuilder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public BookingDTOBuilder endTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public BookingDTOBuilder user(User user) {
            this.user = user;
            return this;
        }

        public BookingDTO build() {
            return new BookingDTO(this.id, this.workplaceId, this.hallId, this.startTime, this.endTime, this.user);
        }

        public String toString() {
            return "BookingDTO.BookingDTOBuilder(id=" + this.id + ", workplaceId=" + this.workplaceId + ", hallId=" + this.hallId + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", user=" + this.user + ")";
        }
    }
}

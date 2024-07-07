package org.example.model;


/** DTO class for user interactions with the booking entity. **/
public class BookingPostRequest {
    String resourceId;
    String resourceType;
    String startDateTimeString;
    String endDateTimeString;

    public static BookingPostRequestBuilder builder() {
        return new BookingPostRequestBuilder();
    }

    public String getResourceId() {
        return this.resourceId;
    }

    public String getResourceType() {
        return this.resourceType;
    }

    public String getStartDateTimeString() {
        return this.startDateTimeString;
    }

    public String getEndDateTimeString() {
        return this.endDateTimeString;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public void setStartDateTimeString(String startDateTimeString) {
        this.startDateTimeString = startDateTimeString;
    }

    public void setEndDateTimeString(String endDateTimeString) {
        this.endDateTimeString = endDateTimeString;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BookingPostRequest)) {
            return false;
        } else {
            BookingPostRequest other = (BookingPostRequest)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$resourceId = this.getResourceId();
                    Object other$resourceId = other.getResourceId();
                    if (this$resourceId == null) {
                        if (other$resourceId == null) {
                            break label59;
                        }
                    } else if (this$resourceId.equals(other$resourceId)) {
                        break label59;
                    }

                    return false;
                }

                Object this$resourceType = this.getResourceType();
                Object other$resourceType = other.getResourceType();
                if (this$resourceType == null) {
                    if (other$resourceType != null) {
                        return false;
                    }
                } else if (!this$resourceType.equals(other$resourceType)) {
                    return false;
                }

                Object this$startDateTimeString = this.getStartDateTimeString();
                Object other$startDateTimeString = other.getStartDateTimeString();
                if (this$startDateTimeString == null) {
                    if (other$startDateTimeString != null) {
                        return false;
                    }
                } else if (!this$startDateTimeString.equals(other$startDateTimeString)) {
                    return false;
                }

                Object this$endDateTimeString = this.getEndDateTimeString();
                Object other$endDateTimeString = other.getEndDateTimeString();
                if (this$endDateTimeString == null) {
                    if (other$endDateTimeString != null) {
                        return false;
                    }
                } else if (!this$endDateTimeString.equals(other$endDateTimeString)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof BookingPostRequest;
    }

    public int hashCode() {
        int result = 1;
        Object $resourceId = this.getResourceId();
        result = result * 59 + ($resourceId == null ? 43 : $resourceId.hashCode());
        Object $resourceType = this.getResourceType();
        result = result * 59 + ($resourceType == null ? 43 : $resourceType.hashCode());
        Object $startDateTimeString = this.getStartDateTimeString();
        result = result * 59 + ($startDateTimeString == null ? 43 : $startDateTimeString.hashCode());
        Object $endDateTimeString = this.getEndDateTimeString();
        result = result * 59 + ($endDateTimeString == null ? 43 : $endDateTimeString.hashCode());
        return result;
    }

    public String toString() {
        return "BookingPostRequest(resourceId=" + this.getResourceId() + ", resourceType=" + this.getResourceType() + ", startDateTimeString=" + this.getStartDateTimeString() + ", endDateTimeString=" + this.getEndDateTimeString() + ")";
    }

    public BookingPostRequest() {
    }

    public BookingPostRequest(String resourceId, String resourceType, String startDateTimeString, String endDateTimeString) {
        this.resourceId = resourceId;
        this.resourceType = resourceType;
        this.startDateTimeString = startDateTimeString;
        this.endDateTimeString = endDateTimeString;
    }

    public static class BookingPostRequestBuilder {
        private String resourceId;
        private String resourceType;
        private String startDateTimeString;
        private String endDateTimeString;

        BookingPostRequestBuilder() {
        }

        public BookingPostRequestBuilder resourceId(String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public BookingPostRequestBuilder resourceType(String resourceType) {
            this.resourceType = resourceType;
            return this;
        }

        public BookingPostRequestBuilder startDateTimeString(String startDateTimeString) {
            this.startDateTimeString = startDateTimeString;
            return this;
        }

        public BookingPostRequestBuilder endDateTimeString(String endDateTimeString) {
            this.endDateTimeString = endDateTimeString;
            return this;
        }

        public BookingPostRequest build() {
            return new BookingPostRequest(this.resourceId, this.resourceType, this.startDateTimeString, this.endDateTimeString);
        }

        public String toString() {
            return "BookingPostRequest.BookingPostRequestBuilder(resourceId=" + this.resourceId + ", resourceType=" + this.resourceType + ", startDateTimeString=" + this.startDateTimeString + ", endDateTimeString=" + this.endDateTimeString + ")";
        }
    }
}

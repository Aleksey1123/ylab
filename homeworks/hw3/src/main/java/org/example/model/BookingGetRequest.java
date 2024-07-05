//package org.example.model;
//
//public class BookingGetRequest {
//    private String id;
//    private String resourceId;
//    private String time;
//    private String username;
//
//    public static BookingGetRequestBuilder builder() {
//        return new BookingGetRequestBuilder();
//    }
//
//    public String getId() {
//        return this.id;
//    }
//
//    public String getResourceId() {
//        return this.resourceId;
//    }
//
//    public String getTime() {
//        return this.time;
//    }
//
//    public String getUsername() {
//        return this.username;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public void setResourceId(String resourceId) {
//        this.resourceId = resourceId;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public boolean equals(Object o) {
//        if (o == this) {
//            return true;
//        } else if (!(o instanceof BookingGetRequest)) {
//            return false;
//        } else {
//            BookingGetRequest other = (BookingGetRequest)o;
//            if (!other.canEqual(this)) {
//                return false;
//            } else {
//                label59: {
//                    Object this$id = this.getId();
//                    Object other$id = other.getId();
//                    if (this$id == null) {
//                        if (other$id == null) {
//                            break label59;
//                        }
//                    } else if (this$id.equals(other$id)) {
//                        break label59;
//                    }
//
//                    return false;
//                }
//
//                Object this$resourceId = this.getResourceId();
//                Object other$resourceId = other.getResourceId();
//                if (this$resourceId == null) {
//                    if (other$resourceId != null) {
//                        return false;
//                    }
//                } else if (!this$resourceId.equals(other$resourceId)) {
//                    return false;
//                }
//
//                Object this$time = this.getTime();
//                Object other$time = other.getTime();
//                if (this$time == null) {
//                    if (other$time != null) {
//                        return false;
//                    }
//                } else if (!this$time.equals(other$time)) {
//                    return false;
//                }
//
//                Object this$username = this.getUsername();
//                Object other$username = other.getUsername();
//                if (this$username == null) {
//                    if (other$username != null) {
//                        return false;
//                    }
//                } else if (!this$username.equals(other$username)) {
//                    return false;
//                }
//
//                return true;
//            }
//        }
//    }
//
//    protected boolean canEqual(Object other) {
//        return other instanceof BookingGetRequest;
//    }
//
//    public int hashCode() {
//        int result = 1;
//        Object $id = this.getId();
//        result = result * 59 + ($id == null ? 43 : $id.hashCode());
//        Object $resourceId = this.getResourceId();
//        result = result * 59 + ($resourceId == null ? 43 : $resourceId.hashCode());
//        Object $time = this.getTime();
//        result = result * 59 + ($time == null ? 43 : $time.hashCode());
//        Object $username = this.getUsername();
//        result = result * 59 + ($username == null ? 43 : $username.hashCode());
//        return result;
//    }
//
//    public String toString() {
//        return "BookingGetRequest(id=" + this.getId() + ", resourceId=" + this.getResourceId() + ", time=" + this.getTime() + ", username=" + this.getUsername() + ")";
//    }
//
//    public BookingGetRequest() {
//    }
//
//    public BookingGetRequest(String id, String resourceId, String time, String username) {
//        this.id = id;
//        this.resourceId = resourceId;
//        this.time = time;
//        this.username = username;
//    }
//
//    public static class BookingGetRequestBuilder {
//        private String id;
//        private String resourceId;
//        private String time;
//        private String username;
//
//        BookingGetRequestBuilder() {
//        }
//
//        public BookingGetRequestBuilder id(String id) {
//            this.id = id;
//            return this;
//        }
//
//        public BookingGetRequestBuilder resourceId(String resourceId) {
//            this.resourceId = resourceId;
//            return this;
//        }
//
//        public BookingGetRequestBuilder time(String time) {
//            this.time = time;
//            return this;
//        }
//
//        public BookingGetRequestBuilder username(String username) {
//            this.username = username;
//            return this;
//        }
//
//        public BookingGetRequest build() {
//            return new BookingGetRequest(this.id, this.resourceId, this.time, this.username);
//        }
//
//        public String toString() {
//            return "BookingGetRequest.BookingGetRequestBuilder(id=" + this.id + ", resourceId=" + this.resourceId + ", time=" + this.time + ", username=" + this.username + ")";
//        }
//    }
//}
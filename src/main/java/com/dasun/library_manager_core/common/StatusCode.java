package com.dasun.library_manager_core.common;

public enum StatusCode {

    S0000 {
        public String description() {
            return "Success";
        }
    },
    I5000 {
        public String description() {
            return "Internal Error";
        }
    },
    E2000 {
        public String description() {
            return "Record not found";
        }
    },
    E2001 {
        public String description() {
            return "Invalid Profile";
        }
    },
    E2002 {
        public String description() {
            return "Invalid profile details";
        }
    },
    E2003 {
        public String description() {
            return "Invalid user Details";
        }
    };
    public abstract String description();


}

package com.mdx.xyphose.usermgmt.entity.enums;

    public enum Status {
        inactive("inactive"),
        active("active");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        // Modify the fromValue method to be case-insensitive
        public static Status fromValue(String value) {
            for (Status status : Status.values()) {
                if (status.value.equalsIgnoreCase(value)) {  // Use equalsIgnoreCase for case-insensitivity
                    return status;
                }
            }
            throw new IllegalArgumentException("Unknown status: " + value);
        }
    }

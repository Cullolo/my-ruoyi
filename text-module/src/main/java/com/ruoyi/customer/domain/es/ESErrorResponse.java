package com.ruoyi.customer.domain.es;


import java.util.List;

/**
 * @Description TODO
 * @Author xianpei.qin
 * @date 2021/03/04 11:35
 */
public class ESErrorResponse {

    private ErrorMsg error;

    private Integer status;

    public class ErrorMsg{

        private String type;

        private String reason;

        private String  line;

        private String col;

        private List<RootCause> root_cause;

        public class RootCause{
            private String type;

            private String reason;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            @Override
            public String toString() {
                return "RootCause{" +
                        "type='" + type + '\'' +
                        ", reason='" + reason + '\'' +
                        '}';
            }
        }

        public List<RootCause> getRoot_cause() {
            return root_cause;
        }

        public void setRoot_cause(List<RootCause> root_cause) {
            this.root_cause = root_cause;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public String getCol() {
            return col;
        }

        public void setCol(String col) {
            this.col = col;
        }

        @Override
        public String toString() {
            return "ErrorMsg{" +
                    "type='" + type + '\'' +
                    ", reason='" + reason + '\'' +
                    ", line='" + line + '\'' +
                    ", col='" + col + '\'' +
                    '}';
        }

    }

    public ErrorMsg getError() {
        return error;
    }

    public void setError(ErrorMsg error) {
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ESErrorResponse{" +
                "error=" + error +
                ", status=" + status +
                '}';
    }
}

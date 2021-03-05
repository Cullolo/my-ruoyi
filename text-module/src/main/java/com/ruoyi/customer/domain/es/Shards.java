package com.ruoyi.customer.domain.es;

import java.util.List;

/**
 * @Description TODO
 * @Author xianpei.qin
 * @date 2021/03/04 13:31
 */
public class Shards {

    private Integer total;

    private Integer successful;

    private Integer skipped;

    private Integer failed;

    private List<Failures> failures;

    class Failures{

        private Integer shard;

        private String index;

        private String node;

        private Reason reason;

        class Reason{

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
                return "Reason{" +
                        "type='" + type + '\'' +
                        ", reason='" + reason + '\'' +
                        '}';
            }
        }

        public Integer getShard() {
            return shard;
        }

        public void setShard(Integer shard) {
            this.shard = shard;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getNode() {
            return node;
        }

        public void setNode(String node) {
            this.node = node;
        }

        public Reason getReason() {
            return reason;
        }

        public void setReason(Reason reason) {
            this.reason = reason;
        }
    }

    public List<Failures> getFailures() {
        return failures;
    }

    public void setFailures(List<Failures> failures) {
        this.failures = failures;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSuccessful() {
        return successful;
    }

    public void setSuccessful(Integer successful) {
        this.successful = successful;
    }

    public Integer getSkipped() {
        return skipped;
    }

    public void setSkipped(Integer skipped) {
        this.skipped = skipped;
    }

    public Integer getFailed() {
        return failed;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    @Override
    public String toString() {
        return "Shards{" +
                "total=" + total +
                ", successful=" + successful +
                ", skipped=" + skipped +
                ", failed=" + failed +
                '}';
    }
}

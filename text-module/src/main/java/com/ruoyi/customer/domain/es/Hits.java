package com.ruoyi.customer.domain.es;

import java.util.List;

/**
 * @Description TODO
 * @Author xianpei.qin
 * @date 2021/03/04 13:34
 */
public class Hits {

    private Integer total;

    private Double max_score;

    private List<HitContent> hits;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getMax_score() {
        return max_score;
    }

    public void setMax_score(Double max_score) {
        this.max_score = max_score;
    }

    public List<HitContent> getHits() {
        return hits;
    }

    public void setHits(List<HitContent> hits) {
        this.hits = hits;
    }

    @Override
    public String toString() {
        return "Hits{" +
                "total=" + total +
                ", max_score=" + max_score +
                ", hits=" + hits +
                '}';
    }
    public class HitContent{
        private String _index;

        private String _type;

        private String _id;

        private String _score;

        private Object _source;

        public String get_index() {
            return _index;
        }

        public void set_index(String _index) {
            this._index = _index;
        }

        public String get_type() {
            return _type;
        }

        public void set_type(String _type) {
            this._type = _type;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String get_score() {
            return _score;
        }

        public void set_score(String _score) {
            this._score = _score;
        }

        public Object get_source() {
            return _source;
        }

        public void set_source(Object _source) {
            this._source = _source;
        }

        @Override
        public String toString() {
            return "HitContent{" +
                    "_index='" + _index + '\'' +
                    ", _type='" + _type + '\'' +
                    ", _id='" + _id + '\'' +
                    ", _score='" + _score + '\'' +
                    ", _source=" + _source +
                    '}';
        }
    }
}

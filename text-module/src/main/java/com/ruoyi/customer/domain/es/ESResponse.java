package com.ruoyi.customer.domain.es;


import java.util.Objects;

/**
 * @Description TODO
 * @Author xianpei.qin
 * @date 2021/03/03 17:25
 */
public class ESResponse {

    /**
     * 查询耗时
     */
    private Integer took;

    /**
     * 是否超时
     */
    private Boolean timed_out;

    /**
     * 分片信息
     */
    private Shards _shards;

    /**
     * 返回的主要信息
     */
    private Hits hits;

    /**
     * 聚合分析时返回
     */
    private Object aggregations;


    public Integer getTook() {
        return took;
    }

    public void setTook(Integer took) {
        this.took = took;
    }

    public Boolean getTimed_out() {
        return timed_out;
    }

    public void setTimed_out(Boolean timed_out) {
        this.timed_out = timed_out;
    }

    public Shards get_shards() {
        return _shards;
    }

    public void set_shards(Shards _shards) {
        this._shards = _shards;
    }

    public Hits getHits() {
        return hits;
    }

    public void setHits(Hits hits) {
        this.hits = hits;
    }

    public Object getAggregations() {
        return aggregations;
    }

    public void setAggregations(Object aggregations) {
        this.aggregations = aggregations;
    }

    @Override
    public String toString() {
        return "ESResponse{" +
                "took=" + took +
                ", timed_out=" + timed_out +
                ", _shards=" + _shards +
                ", hits=" + hits +
                ", aggregations=" + aggregations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ESResponse that = (ESResponse) o;
        return Objects.equals(took, that.took) &&
                Objects.equals(timed_out, that.timed_out) &&
                Objects.equals(_shards, that._shards) &&
                Objects.equals(hits, that.hits) &&
                Objects.equals(aggregations, that.aggregations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(took, timed_out, _shards, hits, aggregations);
    }
}

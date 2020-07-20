package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "follows")
@NamedQueries({
    @NamedQuery(
            name = "getMyAllFollows",
            query = "SELECT f FROM FollowList AS f WHERE f.e_follow = :e_follow ORDER BY f.id DESC"
            ),
    @NamedQuery(
            name = "getMyFollowsCount",
            query = "SELECT COUNT(f) FROM FollowList AS f WHERE f.e_follow = :e_follow"
            ),
    @NamedQuery(
            name = "getMyFollowsId",
            query = "SELECT f.e_followed FROM FollowList AS f WHERE f.e_follow = :e_follow ORDER BY f.id DESC"
            )
})
@Entity
public class FollowList {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "e_follow", nullable = false)
    private Employee e_follow;

    @ManyToOne
    @JoinColumn(name = "e_followed", nullable = false)
    private Employee e_followed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getE_follow() {
        return e_follow;
    }

    public void setE_follow(Employee e_follow) {
        this.e_follow = e_follow;
    }

    public Employee getE_followed() {
        return e_followed;
    }

    public void setE_followed(Employee e_followed) {
        this.e_followed = e_followed;
    }
}

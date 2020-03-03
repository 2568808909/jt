package com.jt.itf.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.solr.client.solrj.beans.Field;

import com.jt.common.po.BasePojo;

@Table(name = "tb_item")
public class Item extends BasePojo {

	@Id // 定义主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
	@Field("id")
	private Long id; // 商品Id
	@Field("title")
	private String title; // 商品标题
	@Field("sellPoint")
	private String sellPoint; // 卖点信息
	@Field("price")
	private Long price; // 商品价格
	@Field("num")
	private Integer num; // 商品数量
	@Field("barcode")
	private String barcode; // 条形码
	@Field("image")
	private String image; // 商品图片信息
	@Field("cid")
	private Long cid; // 商品分类ID
	@Field("status")
	private Integer status; // 商品状态信息 1正常 2下架 3删除

	public String[] getImages() {
		return image.split(",");
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", title=" + title + ", sellPoint=" + sellPoint + ", price=" + price + ", num=" + num
				+ ", barcode=" + barcode + ", image=" + image + ", cid=" + cid + ", status=" + status + "]";
	}
}

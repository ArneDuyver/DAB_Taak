package be.kuleuven.csa.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class ProductTips implements Serializable {
    String name,soort,_id,_rev;
    ArrayList<String> tips,links,recipes,extra_info;
    int product_id;

    public ProductTips(String name, String soort, int product_id) {
        this.name = name;
        this.soort = soort;
        this.product_id = product_id;
        this._id = ""+product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSoort() {
        return soort;
    }

    public void setSoort(String soort) {
        this.soort = soort;
    }

    public ArrayList<String> getTips() {
        return tips;
    }

    public void setTips(ArrayList<String> tips) {
        this.tips = tips;
    }

    public ArrayList<String> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<String> links) {
        this.links = links;
    }

    public ArrayList<String> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<String> recipes) {
        this.recipes = recipes;
    }

    public ArrayList<String> getExtra_info() {
        return extra_info;
    }

    public void setExtra_info(ArrayList<String> extra_info) {
        this.extra_info = extra_info;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTips that = (ProductTips) o;
        if (links != null && that.links != null) {
            for (int i = 0; i<links.size();i++){
                if (!links.get(i).equalsIgnoreCase(that.links.get(i))) return false;
            }
        } else {
            return (links == null && that.links == null);
        }

        if (tips != null && that.tips != null) {
            for (int i = 0; i<tips.size();i++){
                if (!tips.get(i).equalsIgnoreCase(that.tips.get(i))) return false;
            }
        } else {
            return (tips == null && that.tips == null);
        }

        if (recipes != null && that.recipes != null) {
            for (int i = 0; i<recipes.size();i++){
                if (!recipes.get(i).equalsIgnoreCase(that.recipes.get(i))) return false;
            }
        } else {
            return (recipes == null && that.recipes == null);
        }

        if (extra_info != null && that.extra_info != null) {
            for (int i = 0; i<extra_info.size();i++){
                if (!extra_info.get(i).equalsIgnoreCase(that.extra_info.get(i))) return false;
            }
        } else {
            return (extra_info == null && that.extra_info == null);
        }

        return product_id == that.product_id && name.equals(that.name) && soort.equals(that.soort) && _id.equals(that._id) && Objects.equals(tips, that.tips) && Objects.equals(links, that.links) && Objects.equals(recipes, that.recipes) && Objects.equals(extra_info, that.extra_info);
    }

}

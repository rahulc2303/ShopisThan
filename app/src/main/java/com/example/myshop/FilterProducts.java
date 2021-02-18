package com.example.myshop;

import android.widget.Adapter;
import android.widget.Filter;

import com.example.myshop.Adpater.ProductAdapter;
import com.example.myshop.Model.Products;

import java.util.ArrayList;

public class FilterProducts  extends Filter {

    private ProductAdapter adapter;
    private ArrayList<Products> filterList;

    public FilterProducts(ProductAdapter adapter, ArrayList<Products> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }


    @Override
    protected FilterResults performFiltering(CharSequence constraint)
    {
        FilterResults results = new FilterResults();
        //validate data for search query
        if (constraint != null && constraint.length()>0)
        {
            //change to upper case, to make case insensitive
            constraint = constraint.toString().toUpperCase();

            //store our filtered list
            ArrayList<Products> filteredModels = new ArrayList<>();
            for (int  i=0; i<filterList.size();i++)
            {
                if (filterList.get(i).getProductName().toUpperCase().contains(constraint) ||
                        filterList.get(i).getCategory().toUpperCase().contains(constraint))
                {
                    // add filtered data to list
                    filteredModels.add(filterList.get(i));
                }
            }
            results.count = filteredModels.size();
            results.values = filteredModels;
        }

        else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results)
    {
        adapter.productsList = (ArrayList<Products>) results.values;
        adapter.notifyDataSetChanged();

    }
}

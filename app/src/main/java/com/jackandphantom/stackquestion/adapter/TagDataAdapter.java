package com.jackandphantom.stackquestion.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jackandphantom.stackquestion.R;
import com.jackandphantom.stackquestion.model.TagData;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
/*
*
* This is adapter for the tag which will shown at the beginning of application
* so it will loadds tag from the stackoverflower api for tags
* */

public class TagDataAdapter extends RecyclerView.Adapter<TagDataAdapter.ViewHolder> {

    //List of tag data refer to link{@TagData}
    private List<TagData> tagDataList;
    private LayoutInflater layoutInflater;
    OnClickListener onClickListener;

    /**
     * This interface help to listen click event on the recylerview items
     * @param {tag} which is string help to call the stackoverflow questions
     * @param {postion} from where it calls position of selected items from the recyclerview.
     * */
    public interface OnClickListener {
        void onClick(String tag, int position);
    }

    public TagDataAdapter(List<TagData> tagDataList, Context context) {
        this.tagDataList = tagDataList;
        layoutInflater = LayoutInflater.from(context);
    }

    //Create the viewholder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.tag_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.tagName.setText(tagDataList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return tagDataList.size();
    }

    public void setOnClickListener(OnClickListener  onClickListener) {
        this.onClickListener = onClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tagName;
         ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagName = itemView.findViewById(R.id.tag);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("MY TAG", "ITEMVIEW");
                    if (onClickListener != null) {
                        onClickListener.onClick(tagDataList.get(getAdapterPosition()).getName(),
                                getAdapterPosition());
                    }
                }
            });
        }
    }
}

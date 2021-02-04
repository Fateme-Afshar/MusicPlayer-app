package com.example.musicplayer_app.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer_app.R;
import com.example.musicplayer_app.databinding.ItemMusicBinding;
import com.example.musicplayer_app.model.Music;

import java.util.HashMap;
import java.util.Map;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.Holder> implements Filterable {
    private final Map<Integer, Music> mMusicList = new HashMap<>();

    private Map<Integer, Music> mSearchResults;

    private Context mContext;

    private MusicAdapterCallback mCallback;

    public void setCallback(MusicAdapterCallback callback) {
        mCallback = callback;
    }

    public MusicAdapter(Context context, Map<Integer,Music> musicList) {
        mContext = context.getApplicationContext();
        mMusicList.putAll(musicList);
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMusicBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.item_music,
                parent,
                false);

        return new Holder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mMusicList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.sendMusicInfo(mMusicList.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMusicList.size();
    }

  public   class Holder extends RecyclerView.ViewHolder {
        private ItemMusicBinding mBinding;

        public Holder(@NonNull ItemMusicBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void bind(Music music) {
            mBinding.setMusic(music);
        }
    }
    public interface MusicAdapterCallback {
        void sendMusicInfo(Music music, int position);
    }

    private Filter mFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Map<Integer, Music> filterList = new HashMap<>();

            if (constraint==null || constraint.length()==0)
                filterList.putAll(mSearchResults);
            else {
                String pattern = constraint.toString().toLowerCase().trim();

                for (int i = 0; i < mMusicList.size(); i++) {
                    Music music = mMusicList.get(i);
                    if (music.getName().contains(pattern) ||
                            music.getSingerName().contains(pattern))
                        filterList.put(i, music);
                }
            }

            FilterResults filterResults=new FilterResults();
            filterResults.values=filterList;
            return filterResults;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mMusicList.clear();
            mMusicList.putAll((Map<? extends Integer, ? extends Music>) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return mFilter;
    }
}

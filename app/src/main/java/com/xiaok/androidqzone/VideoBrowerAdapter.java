package com.xiaok.androidqzone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.xiaok.androidqzone.custom.CircleImageView;

import java.util.LinkedList;

public class VideoBrowerAdapter extends BaseAdapter {

    private LinkedList<VideoBrower> aData;
    private Context mContext;
    private boolean isGood = false;
    private boolean isVideoPlaying = false;
    private int commentIndex = 4;

    private TextView thumpUpView;

    public VideoBrowerAdapter(LinkedList<VideoBrower> aData, Context mContext){
        this.aData = aData;
        this.mContext = mContext;
    }
    @Override
    public int getCount(){
        return aData.size();
    }
    @Override
    public Object getItem(int position){
        return null;
    }
    @Override
    public long getItemId(int position){
        return position;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.video_brower_item,parent,false);
            holder = new ViewHolder();
            holder.videoAvatar = convertView.findViewById(R.id.video_avatar);
            holder.username = convertView.findViewById(R.id.video_username);
            holder.videoDate = convertView.findViewById(R.id.video_date);
            holder.videoDescripation = convertView.findViewById(R.id.video_descripation);
            holder.video_view = convertView.findViewById(R.id.video_view);
            holder.videoPosition = convertView.findViewById(R.id.video_position);
            holder.videoPlay = convertView.findViewById(R.id.video_play);
            holder.video_iv_good = convertView.findViewById(R.id.video_iv_good);
            holder.video_iv_comment = convertView.findViewById(R.id.video_iv_comment);
            holder.video_iv_share = convertView.findViewById(R.id.video_iv_share);
            holder.video_et_comment = convertView.findViewById(R.id.video_et_comment);

            holder.mContainer = convertView.findViewById(R.id.mContainer); //拿到布局，用于动态添加View
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        Uri videoUri = Uri.parse(aData.get(position).getVideoPath());
        media.setDataSource(mContext,videoUri);
        Bitmap bitmap = media.getFrameAtTime();

        holder.videoAvatar.setImageResource(aData.get(position).getAvatarId());
        holder.username.setText(aData.get(position).getUsername());
        holder.videoDate.setText(aData.get(position).getDate());
        holder.videoDescripation.setText(aData.get(position).getVideoDescripation());
        holder.video_view.setBackground(new BitmapDrawable(bitmap));
        holder.video_view.setVideoURI(videoUri);
        holder.videoPosition.setText(aData.get(position).getPosition());

        /*
        *响应事件
         */
        //播放视频按钮
        holder.videoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.video_view != null){
                    if (!isVideoPlaying){
                        holder.videoPlay.setImageResource(R.mipmap.ic_record_stop);
                        holder.video_view.start();
                        holder.video_view.setBackground(null);
                        holder.video_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                holder.videoPlay.setImageResource(R.mipmap.ic_record_play);
                            }
                        });
                    }else {
                        holder.video_view.stopPlayback();
                        holder.videoPlay.setImageResource(R.mipmap.ic_record_play);
                    }

                }
            }
        });

        //点赞
        holder.video_iv_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGood){
                    holder.video_iv_good.setImageResource(R.mipmap.video_share_good_blue);
                    addThumpUpView(holder);
                    Toast.makeText(mContext, "点赞成功！", Toast.LENGTH_SHORT).show();
                    isGood = true;
                }else {
                    holder.video_iv_good.setImageResource(R.mipmap.video_share_good);
                    removeThumpUpView(holder);
                    isGood = false;
                }

            }
        });

        //评论图片按钮
        holder.video_iv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //焦点移动到评论区
                holder.video_et_comment.requestFocus();
            }
        });

        //评论框右边发表图标
        holder.video_et_comment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = holder.video_et_comment.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > holder.video_et_comment.getWidth()
                        - holder.video_et_comment.getPaddingRight()
                        - drawable.getIntrinsicWidth()){
                    //发表
                    String commentStr = holder.video_et_comment.getText().toString().trim();
                    if (TextUtils.isEmpty(commentStr)){
                        Toast.makeText(mContext,"评论内容不能为空！",Toast.LENGTH_SHORT).show();
                    }else {
                        addView(holder, commentStr);
                        holder.video_et_comment.setText(""); //发表完评论后编辑框清空
                        Toast.makeText(mContext,"发表成功!",Toast.LENGTH_SHORT).show();
                    }

                }
                return false;
            }
        });


        return convertView;
    }
    static class ViewHolder{
        CircleImageView videoAvatar;
        TextView username;
        TextView videoDate;
        TextView videoDescripation;
        VideoView video_view;
        TextView videoPosition;
        ImageView videoPlay;
        ImageView video_iv_good;
        ImageView video_iv_comment;
        ImageView video_iv_share;
        EditText video_et_comment;
        LinearLayout mContainer;
    }

    private void addView(ViewHolder holder, String commentStr){
        TextView view = new TextView(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60);
        lp.setMargins(0,15,0,15);
        view.setTextSize(14);
        view.setTextColor(mContext.getColor(R.color.record_comment_text));
        view.setText("xiaok:"+commentStr);
        int index = holder.mContainer.getChildCount();
        holder.mContainer.addView(view,index-1,lp);
    }

    private void addThumpUpView(ViewHolder holder){
        thumpUpView = new TextView(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60);
        lp.setMargins(0,15,0,15);
        thumpUpView.setTextSize(14);
        thumpUpView.setTextColor(mContext.getColor(R.color.record_comment_text));
        thumpUpView.setText("xiaok 觉得很赞");
        holder.mContainer.addView(thumpUpView,3,lp);
    }

    private void removeThumpUpView(ViewHolder holder){
        holder.mContainer.removeViewAt(3);
    }
}

package in.co.vsys.myssksamaj.model;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import in.co.vsys.myssksamaj.R;

public class ListChatViewHolder extends RecyclerView.ViewHolder {

    private final View mView;
    private CircleImageView profileImage;
    private TextView row_chatListName, row_chatListLastMessage;


    public ListChatViewHolder(View itemView) {
        super(itemView);

        mView = itemView;

        profileImage = mView.findViewById(R.id.row_chatListImage);
        row_chatListLastMessage = mView.findViewById(R.id.row_chatListLastMessage);
        row_chatListName = mView.findViewById(R.id.row_chatListName);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
    }

    public void setLastMessage(String lastMessage) {

        if (lastMessage == null || lastMessage.isEmpty()) {

            row_chatListLastMessage.setText("");

        } else {

            row_chatListLastMessage.setText(lastMessage);
        }


    }

    public void setPhotoImage(String image) {

        try {

            if (!TextUtils.isEmpty(image) && image.length() > 10) {

                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.img_preview)
                        .error(R.drawable.img_preview)
                        .into(profileImage);
            } else {

                Picasso.get()
                        .load(R.drawable.img_preview)
                        .placeholder(R.drawable.img_preview)
                        .into(profileImage);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    public void setUserName(String name) {

        if (name == null || name.isEmpty()) {

            row_chatListName.setText("");
        } else {

            row_chatListName.setText(name);
        }

    }

    public String setUserId(String id) {

        return id;
    }

    private ListChatViewHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener {

        public void onItemClick(View view, int position);

    }

    public void setOnClickListener(ListChatViewHolder.ClickListener clickListener) {
        mClickListener = clickListener;
    }
}

package com.example.abe.myapplication.perfil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.abe.myapplication.R;
import com.example.abe.myapplication.utils.Utils;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import de.hdodenhof.circleimageview.CircleImageView;


/*A ideia aqui é, em cada posição da recyclerView, usar um novo layout*/
public class EditPerfilAdapter extends  RecyclerView.Adapter<EditPerfilAdapter.EditPerfilViewHolder>{
    //Itens que serão usados na RecyclerView
    private int[] items;
    private final int PHOTO = 0, NAME = 1, GPS = 2, RADIUS = 3, MAP = 4, BUTTONS = 5;
    public static final int PICK_IMAGE = 1234;
    private int seekBarValue;
    private boolean checkBool;

    public EditPerfilAdapter(int[] items){
        this.items = items;
    }

    @NonNull
    @Override
    public EditPerfilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EditPerfilViewHolder viewHolder;
        Context context = parent.getContext();
        LayoutInflater inflater;
        int layoutId;
        View view;

        switch (viewType) { //arrumar os layouts
            case 0:
                layoutId = R.layout.edit_profile_photo; //Mudar aqui
                inflater = LayoutInflater.from(context);
                view = inflater.inflate(layoutId, parent, false);
                viewHolder = new EditPerfilAdapter.EditPerfilViewHolder(view);
                break;
            case 1:
                layoutId = R.layout.edit_profile_name; //Mudar aqui
                inflater = LayoutInflater.from(context);
                view = inflater.inflate(layoutId, parent, false);
                viewHolder = new EditPerfilAdapter.EditPerfilViewHolder(view);
                break;
            case 2:
                layoutId = R.layout.edit_profile_gps; //Mudar aqui
                inflater = LayoutInflater.from(context);
                view = inflater.inflate(layoutId, parent, false);
                viewHolder = new EditPerfilAdapter.EditPerfilViewHolder(view);
                break;
            case 3:
                layoutId = R.layout.edit_profile_range; //Mudar aqui
                inflater = LayoutInflater.from(context);
                view = inflater.inflate(layoutId, parent, false);
                viewHolder = new EditPerfilAdapter.EditPerfilViewHolder(view);
                break;
            case 4:
                layoutId = R.layout.edit_profile_map; //Mudar aqui
                inflater = LayoutInflater.from(context);
                view = inflater.inflate(layoutId, parent, false);
                viewHolder = new EditPerfilAdapter.EditPerfilViewHolder(view);
                break;
            default:
                layoutId = R.layout.edit_profile_empty; //Mudar aqui
                inflater = LayoutInflater.from(context);
                view = inflater.inflate(layoutId, parent, false);
                viewHolder = new EditPerfilAdapter.EditPerfilViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EditPerfilViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return this.items.length;
    }

    //Escolhe o tipo do layout
    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return PHOTO;
        }else if(position == 1){
            return NAME;
        }else if(position == 2){
            return GPS;
        }else if(position == 3){
            return RADIUS;
        }else if(position == 4){
            return MAP;
        }
        return -1;
    }

    class EditPerfilViewHolder extends RecyclerView.ViewHolder{
        private CheckBox checkBox;
        private TextView name, atualSeek;
        private CircleImageView image;
        private SeekBar seekbar;
        private Button saveButton;

        public EditPerfilViewHolder(View itemView) {
            super(itemView);

            seekbar = itemView.findViewById(R.id.simpleSeekBar);
            atualSeek = itemView.findViewById(R.id.atualSeekBar);
            name = itemView.findViewById(R.id.textViewNameEditPerfil);
            image = itemView.findViewById(R.id.perfilImageView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }

        public void bind(final int position){
            switch (position){
                case 0:
                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Context myContext = v.getContext();
                            Intent i = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                            ((Activity) myContext).startActivityForResult(Intent.createChooser(i, "Selecione uma imagem"), PICK_IMAGE);
                        }
                    });

                    if(ParseUser.getCurrentUser().getParseFile("imagemPerfil") == null){
                        image.setImageResource(R.drawable.default_icon);
                    }else{
                        Utils utils = new Utils();
                        ParseFile tempPhoto = ParseUser.getCurrentUser().getParseFile("imagemPerfil");
                        utils.loadImagesCircle(tempPhoto, image);
                    }
                    break;
                case 1:
                    name.setText(ParseUser.getCurrentUser().getUsername());
                    break;
                case 2:
                    checkBox.setChecked(ParseUser.getCurrentUser().getBoolean("gpsEnable"));
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            checkBool = checkBox.isChecked();
                            ParseUser.getCurrentUser().put("gpsEnable", checkBool);
                            notifyDataSetChanged();
                            ParseUser.getCurrentUser().saveInBackground();
                        }
                    });
                    break;
                case 3:
                    seekbar.setMax(30);
                    seekbar.setProgress((int)ParseUser.getCurrentUser().getNumber("raioInteresse"));
                    atualSeek.setText(String.valueOf((int)ParseUser.getCurrentUser().getNumber("raioInteresse")));
                    seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if(fromUser){
                                seekbar.setProgress(progress);
                                seekBarValue = progress;
                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                            seekbar.setProgress((int)ParseUser.getCurrentUser().getNumber("raioInteresse"));
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            ParseUser.getCurrentUser().put("raioInteresse", seekBarValue);
                            atualSeek.setText(String.valueOf(seekBarValue));
                            notifyDataSetChanged();
                            ParseUser.getCurrentUser().saveInBackground();
                        }
                    });
                    break;
                case 4:
                    break;

                default:
                    ParseUser.getCurrentUser().saveInBackground();
                    break;
            }
        }
    }
}

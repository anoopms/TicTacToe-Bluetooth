package com.avantaj.android.tictactoe;

import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;

import com.avantaj.android.ttt.R;

public class TTTTheme {
	
	private static TTTTheme instance = new TTTTheme();
	
	private int selectedIndex = 0;
	private int iconSelectedIndex = 0;
	
	private ArrayList<ImageGroup> allImages  = new ArrayList<ImageGroup>();
	private ArrayList<IconGroup> allIcons  = new ArrayList<IconGroup>();
	
	private TTTTheme() {
		
		addImages();
		addIcons();
		
	}
	
	private void addImages(){
		allImages.add(new ImageGroup( 
				 R.drawable.wood_bg,  R.drawable.line_ver, R.drawable.line_hor, 
				 R.drawable.button_bg));
		
		allImages.add(new ImageGroup( 
				 R.drawable.steel_bg,  R.drawable.steel_line_ver, R.drawable.steel_line_hor, 
				 R.drawable.steel_button));
		
	}
	
	private void addIcons(){
		allIcons.add(new IconGroup(R.drawable.tick_normal_1_60_60, R.drawable.cross_normal_1_60_60, 
				 R.drawable.tick_winner_1_60_60, R.drawable.cross_winner_1_60_60));
		allIcons.add(new IconGroup(R.drawable.tick_heart_60_60, R.drawable.cross_heart_60_60, 
				 R.drawable.tick_heart_winner_60_60, R.drawable.cross_heart_winner_60_60));
	}
	
	public static TTTTheme getInstance() {
		return instance;
	}
	
	public int getSelectedIndex() {
		return selectedIndex;
	}
	
	public int getIconSelectedIndex() {
		return iconSelectedIndex;
	}
	

	public void setThemeIndexes(int selectedIndex, int iconSelectionIndex) {
		if(selectedIndex>=allImages.size()){
			Log.d("INDEX_INVALID", "TTTImageSettings.setSelectedIndex invalid "+selectedIndex);
			selectedIndex = 0;
		}
		if(iconSelectionIndex>=allIcons.size()){
			Log.d("INDEX_INVALID", "TTTImageSettings.iconSelectionIndex invalid "+iconSelectionIndex);
			iconSelectionIndex = 0;
		}
		
		this.selectedIndex = selectedIndex;
		this.iconSelectedIndex = iconSelectionIndex;	
	}
	
	public void setTheme(Activity act) {
		
		
		ImageGroup group = getImageGroup();
		IconGroup iconGroup = getIconGroup();
		
		act.findViewById(R.id.mainLayout).setBackgroundResource(group.getBgImage());
		
		act.findViewById(R.id.reset_button).setBackgroundResource(group.getButtonBgImage());
		act.findViewById(R.id.reset_score_button).setBackgroundResource(group.getButtonBgImage());
		
		act.findViewById(R.id.settings_selection).setBackgroundResource(group.getButtonBgImage());
		
		ImageView iv = (ImageView)act.findViewById(R.id.select_mark_tick_image);
		iv.setBackgroundResource(group.getButtonBgImage());
		iv.setImageResource(iconGroup.getTickImage());
		
		iv = (ImageView)act.findViewById(R.id.select_mark_cross_image);
		iv.setBackgroundResource(group.getButtonBgImage());
		iv.setImageResource(iconGroup.getCrossImage());
		
		iv = (ImageView)act.findViewById(R.id.score_icon_tick);
		iv.setImageResource(iconGroup.getTickImage());
		
		iv = (ImageView)act.findViewById(R.id.score_icon_cross);
		iv.setImageResource(iconGroup.getCrossImage());
		
		act.findViewById(R.id.line_hor_1).setBackgroundResource(group.getSeperatorImageHorizontal());
		act.findViewById(R.id.line_hor_2).setBackgroundResource(group.getSeperatorImageHorizontal());
		
		act.findViewById(R.id.line_ver_1).setBackgroundResource(group.getSeperatorImageVertical());
		act.findViewById(R.id.line_ver_2).setBackgroundResource(group.getSeperatorImageVertical());
		act.findViewById(R.id.line_ver_3).setBackgroundResource(group.getSeperatorImageVertical());
		act.findViewById(R.id.line_ver_4).setBackgroundResource(group.getSeperatorImageVertical());
		act.findViewById(R.id.line_ver_5).setBackgroundResource(group.getSeperatorImageVertical());
		act.findViewById(R.id.line_ver_6).setBackgroundResource(group.getSeperatorImageVertical());
		
		
		
	}
	
	public ImageGroup getImageGroup() {
		return allImages.get(selectedIndex);
	}
	
	public IconGroup getIconGroup() {
		return allIcons.get(iconSelectedIndex);
	}
	
	public static class ImageGroup{

		
		private int bgImage;
		private int seperatorImageVertical;
		private int seperatorImageHorizontal;
		private int buttonBgImage;
		
		
		public ImageGroup(

							int bgImage, 
							int seperatorImageVertical,
							int seperatorImageHorizontal,
							int buttonBgImage) {

			this.bgImage = bgImage;
			this.seperatorImageVertical = seperatorImageVertical;
			this.seperatorImageHorizontal = seperatorImageHorizontal;
			this.buttonBgImage = buttonBgImage;
		}

		public int getBgImage() {
			return bgImage;
		}
		
		public int getSeperatorImageHorizontal() {
			return seperatorImageHorizontal;
		}
		
		public int getSeperatorImageVertical() {
			return seperatorImageVertical;
		}
		
		public int getButtonBgImage() {
			return buttonBgImage;
		}
	}
	
	public static class IconGroup{
		private int tickImage;
		private int crossImage;
		
		private int tickImageWinner;
		private int crossImageWinner;
		
		
		public IconGroup(
							int tickImage, 
							int crossImage, 
							int tickImageWinner, 
							int crossImageWinner 
		) {
			this.tickImage = tickImage;
			this.crossImage = crossImage;
			this.tickImageWinner = tickImageWinner;
			this.crossImageWinner = crossImageWinner;
		
		}
		public int getTickImage() {
			return tickImage;
		}
		
		public int getCrossImage() {
			return crossImage;
		}
		
		public int getTickImageWinner() {
			return tickImageWinner;
		}
		
		public int getCrossImageWinner() {
			return crossImageWinner;
		}
	}

}

package utilz;

import main.java.com.example.Game;

public class Constants {

	public static final float GRAVITY = 0.04f * Game.SCALE;
	public static final int ANI_SPEED= 25;

	public static class UI {
		
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT=140;
			public static final int B_HEIGHT_DEFAULT=56;
			public static final int B_WIDTH=(int)(B_WIDTH_DEFAULT*Game.SCALE);
			public static final int B_HEIGHT=(int)(B_HEIGHT_DEFAULT*Game.SCALE);
		}

		public static class PauseButtons {
			public static final int SOUND_SIZE_DEFAULT=42;
			public static final int SOUND_SIZE= (int)(SOUND_SIZE_DEFAULT*Game.SCALE);
		}

		public static class UrmButtons {
			public static final int URM_SIZE_DEFAULT=56;
			public static final int URM_SIZE= (int)(URM_SIZE_DEFAULT*Game.SCALE);
		}

		public static class VolumeButtons {
			public static final int VOLUME_DEFAULT_WIDTH=28;
			public static final int VOLUME_DEFAULT_HEIGHT=44;
			public static final int SLIDER_DEFAULT_WIDTH=215;
			
			public static final int VOLUME_WIDTH= (int) (VOLUME_DEFAULT_WIDTH*Game.SCALE);
			public static final int VOLUME_HEIGHT= (int) (VOLUME_DEFAULT_HEIGHT*Game.SCALE);
			public static final int SLIDER_WIDTH=(int) (SLIDER_DEFAULT_WIDTH*Game.SCALE);
		}
	}

	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants {
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMP = 2;
		public static final int FALLING = 3;
		public static final int ATTACK = 4;
		public static final int HIT=5;
		public static final int DEAD=6;

		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
			case DEAD:
				return 8;
			case RUNNING:
				return 6;
			case IDLE:
				return 5;
			case HIT:
				return 4;
			case JUMP:
			case ATTACK:
				return 3;
			case FALLING:
			default:
				return 1;
			}
		}
	}

	public static class EnemyConstants {
			//Enemy States
			public static final int ENEMY_CRAB=0;
			public static final int IDLE=0;
			public static final int RUNNING=1;
			public static final int ATTACK=2;
			public static final int HIT=3;
			public static final int DEAD=4;

			//Enemy Interactions
			public static int GetMaxHealth(int enemy_Type) {
				switch (enemy_Type) {
					case ENEMY_CRAB:
						return 10;
					default:
						return 1;
				}
			}

			public static int GetEnemyDamage(int enemy_Type) {
				switch (enemy_Type) {
					case ENEMY_CRAB:
						return 15;
					default:
						return 0;
				}
			}
			
			//Dimensions
			public static final int CRAB_DEFAULT_WIDTH=72;
			public static final int CRAB_DEFAULT_HEIGHT=32;
			public static final int CRAB_ACTUAL_WIDTH=(int)(CRAB_DEFAULT_WIDTH*Game.SCALE);
			public static final int CRAB_ACTUAL_HEIGHT=(int)(CRAB_DEFAULT_HEIGHT*Game.SCALE);

			public static final int CRAB_DRAWOFFSET_X=(int)(26*Game.SCALE);
			public static final int CRAB_DRAWOFFSET_Y=(int)(9*Game.SCALE);

			public static int GetSpriteAmount(int enemy_type, int enemy_state) {
				switch (enemy_type) {
				case ENEMY_CRAB:
					switch(enemy_state) {
						case IDLE:
							return 9;
						case RUNNING:
							return 6;
						case ATTACK: 
							return 7;
						case HIT:
							return 4;
						case DEAD:
							return 5;
					}
			}
			return 0;
		}
	}

}
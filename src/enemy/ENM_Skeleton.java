package enemy;

import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;
import main.Utils;

public class ENM_Skeleton extends Entity {

	public ENM_Skeleton(GamePanel gp, int x, int y) {
		super(gp);

		this.worldX = x;
		this.worldY = y;
		
		this.setMaxHealth(80);
		this.setHealth(getMaxHealth());

		this.setSpeed(2);
		this.setDirection("down");

		this.setSolidArea(new Rectangle(8, 16, 32, 32));
		loadSprites();
	}

	private void loadSprites() {
		Utils utils = new Utils();

		this.sprite.up.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_left_1.png", GamePanel.tileSize, GamePanel.tileSize));
		this.sprite.up.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_left_2.png", GamePanel.tileSize, GamePanel.tileSize));
		this.sprite.up.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_left_3.png", GamePanel.tileSize, GamePanel.tileSize));
		this.sprite.up.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_left_4.png", GamePanel.tileSize, GamePanel.tileSize));

		this.sprite.down
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_right_1.png", GamePanel.tileSize, GamePanel.tileSize));
		this.sprite.down
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_right_2.png", GamePanel.tileSize, GamePanel.tileSize));
		this.sprite.down
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_right_3.png", GamePanel.tileSize, GamePanel.tileSize));
		this.sprite.down
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_right_4.png", GamePanel.tileSize, GamePanel.tileSize));

		this.sprite.left
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_right_1.png", GamePanel.tileSize, GamePanel.tileSize));
		this.sprite.left
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_right_2.png", GamePanel.tileSize, GamePanel.tileSize));
		this.sprite.left
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_right_3.png", GamePanel.tileSize, GamePanel.tileSize));
		this.sprite.left
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_right_4.png", GamePanel.tileSize, GamePanel.tileSize));

		this.sprite.right
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_left_1.png", GamePanel.tileSize, GamePanel.tileSize));
		this.sprite.right
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_left_2.png", GamePanel.tileSize, GamePanel.tileSize));
		this.sprite.right
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_left_3.png", GamePanel.tileSize, GamePanel.tileSize));
		this.sprite.right
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_left_4.png", GamePanel.tileSize, GamePanel.tileSize));
	}

}

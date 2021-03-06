package net.runelite.client.plugins.clanmanmode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.client.game.ClanManager;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;

@Singleton
public class ClanManModeOverlay extends Overlay
{
	private final ClanManModeService ClanManModeService;
	private final ClanManModeConfig config;

	@Inject
	private ClanManModeOverlay(ClanManModeConfig config, ClanManModeService ClanManModeService)
	{
		this.config = config;
		this.ClanManModeService = ClanManModeService;
		setPosition(OverlayPosition.DYNAMIC);
		setPriority(OverlayPriority.MED);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		ClanManModeService.forEachPlayer((player, color) -> renderPlayerOverlay(graphics, player, color));
		return null;
	}

	private void renderPlayerOverlay(Graphics2D graphics, Player actor, Color color)
	{
		if (!config.drawOverheadPlayerNames())
		{
			return;
		}

		String name = actor.getName().replace('\u00A0', ' ');
		int offset = actor.getLogicalHeight() + 40;
		Point textLocation = actor.getCanvasTextLocation(graphics, name, offset);

		if (textLocation != null)
		{
			if (config.getClanAttackableColor().equals(color) && config.ShowBold()) {
				graphics.setFont(FontManager.getRunescapeBoldFont());
			}
			OverlayUtil.renderTextLocation(graphics, textLocation, name, color);
		}
	}
}

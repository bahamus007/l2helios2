/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package l2tserver.gameserver.network.clientpackets;

import gnu.trove.TIntIntHashMap;
import l2tserver.gameserver.datatables.AbilityTable;
import l2tserver.gameserver.model.actor.instance.L2PcInstance;
import l2tserver.gameserver.network.serverpackets.ExAcquireAPSkillList;

/**
 * @author Pere
 */
public final class RequestAPReset extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}
	
	/**
	 * @see l2tserver.util.network.BaseRecievePacket.ClientBasePacket#runImpl()
	 */
	@Override
	protected void runImpl()
	{
		L2PcInstance player = getClient().getActiveChar();
		if (player == null)
			return;
		
		if (!player.reduceAdena("Ability points", AbilityTable.getInstance().getAdenaCostForReset(), player, true))
		{
			sendPacket(new ExAcquireAPSkillList(getClient().getActiveChar(), false));
			return;
		}
		
		player.setAbilities(new TIntIntHashMap());
		sendPacket(new ExAcquireAPSkillList(getClient().getActiveChar(), true));
	}
	
	/**
	 * @see l2tserver.gameserver.BasePacket#getType()
	 */
	@Override
	public String getType()
	{
		return "RequestAPReset";
	}
	
}
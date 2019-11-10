/*
 * Copyright 2019 5kWBassMachine <5kWBassMachine@gmail.com> <fivek.ddns.net>
 *
 * This file is part of CustomCommands.
 *
 * CustomCommands is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CustomCommands is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CustomCommands.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.ddns.fivek.customcommands.utility;

import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Utils {

    public static String arrayToString(String[] array) {
        Iterator iterator = Arrays.asList(array).iterator();
        String response = "";
        while (iterator.hasNext()) {
            response += iterator.next() + " ";
        }
        return response;
    }

    public static String arrayToString(List array) {
        Iterator it = array.iterator();
        String response = "";
        while (it.hasNext()) {
            response += " " + it.next().toString();
        }
        return response;
    }

    public static List<String> arrayToArryList(String[] array) {
        Iterator iterator = Arrays.asList(array).iterator();
        List<String> response = new ArrayList<String>();
        while (iterator.hasNext()) {
            response.add(iterator.next().toString());
        }
        return response;
    }

    /**
     * This method removes all formatting keys.
     *
     * @param message A String containing a formatted message.
     * @return A new String without formatting.
     */
    public static ChatComponentText removeFormatting(ChatComponentText message) {
        LogHelper.info(message.toString());

        return new ChatComponentText(message.getUnformattedTextForChat());
    }
}
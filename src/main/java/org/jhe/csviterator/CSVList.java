/*
    Copyright (C) 2006-2011  le.jhe@freesbee.fr

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */
package org.jhe.csviterator;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author le.jhe@freesbee.fr
 * @license GPL v3
 *
 */
public interface CSVList extends List<Map<String,String>> {
	public List<String> getHeaders() throws IOException;

}

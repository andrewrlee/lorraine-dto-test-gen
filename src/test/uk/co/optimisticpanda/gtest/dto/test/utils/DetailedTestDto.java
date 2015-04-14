/*
 * Copyright 2009 Andy Lee.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.co.optimisticpanda.gtest.dto.test.utils;

import java.util.Date;

/**
 * @author Andy Lee
 *
 */
public class DetailedTestDto {
        /**
         * 
         */
        public final String name;
       
		/**
         * @return  name
         */
        public String getName() {
            return name;
        }

        /**
         * 
         */
        public final Date date;

        private final int number;

        /**
         * @param name
         * @param date
         * @param number
         */
        public DetailedTestDto(String name, Date date, int number) {
            this.name = name;
            this.date = date;
            this.number = number;
        }
        
        /**
         * Default constructor
         */
        public DetailedTestDto() {
            this.name = null;
            this.date = null;
            this.number = 3;
        }
        
        /**
         * @see java.lang.Object#hashCode()
         */
        @Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((date == null) ? 0 : date.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + number;
			return result;
		}

		/**
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DetailedTestDto other = (DetailedTestDto) obj;
			if (date == null) {
				if (other.date != null)
					return false;
			} else if (!date.equals(other.date))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (number != other.number)
				return false;
			return true;
		}
    }


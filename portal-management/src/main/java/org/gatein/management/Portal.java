/*
 * JBoss, a division of Red Hat
 * Copyright 2011, Red Hat Middleware, LLC, and individual
 * contributors as indicated by the @authors tag. See the
 * copyright.txt in the distribution for a full listing of
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.gatein.management;

import org.gatein.management.spi.stats.PortalStatisticService;

/**
 * @author Chris Laprun
 * @version $Revision: 8784 $
 */
public class Portal extends ManagedResource<Portal, PortalStatisticService>
{
   public Portal(ResourceKey key, PortalStatisticService statisticService)
   {
      super(key, statisticService);
   }

   public double getThroughput()
   {
      return statisticService.getThroughput();
   }

   public static class PortalKey implements Comparable<PortalKey>
   {
      private static final String SEPARATOR = "@";
      private final String portalName;
      private final String portalContainerName;

      private PortalKey(String portalContainerName, String portalName)
      {
         this.portalName = portalName;
         this.portalContainerName = portalContainerName;
      }

      @Override
      public boolean equals(Object o)
      {
         if (this == o)
         {
            return true;
         }
         if (o == null || getClass() != o.getClass())
         {
            return false;
         }

         PortalKey portalKey = (PortalKey)o;

         return getPortalContainerName().equals(portalKey.getPortalContainerName()) && getPortalName().equals(portalKey.getPortalName());

      }

      @Override
      public int hashCode()
      {
         int result = getPortalName().hashCode();
         result = 31 * result + getPortalContainerName().hashCode();
         return result;
      }

      public static PortalKey parse(String compositeName)
      {
         String[] split = compositeName.split(SEPARATOR);
         return new PortalKey(split[0], split[1]);
      }

      public static String compose(PortalKey key)
      {
         return key.getPortalContainerName() + SEPARATOR + key.getPortalName();
      }

      public int compareTo(PortalKey o)
      {
         return compose(this).compareTo(compose(o));
      }

      public String getPortalName()
      {
         return portalName;
      }

      public String getPortalContainerName()
      {
         return portalContainerName;
      }

      @Override
      public String toString()
      {
         return "'" + portalName + "' portal running in '" + portalContainerName + "' container";
      }

      public static PortalKey create(String portalContainerName, String portalName)
      {
         return new PortalKey(portalContainerName, portalName);
      }
   }
}

#region License
/* Copyright (c) 2008, Katharine Berry
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Katharine Berry nor the names of any contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY KATHARINE BERRY ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL KATHARINE BERRY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
#endregion
using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.IO;
using MiniHttpd;
using OpenMetaverse;

namespace deviMobile.Html
{
    public class LoginDetails : IFile
    {
        #region IFile Members

        public LoginDetails(string name, IDirectory parent, Dictionary<Guid, User> users)
        {
            this.name = name;
            this.parent = parent;
            this.users = users;
        }

        private string name;
        private IDirectory parent;
        private Dictionary<Guid, User> users;

        public string ContentType
        {
            get { return "application/json"; }
        }

        public void OnFileRequested(HttpRequest request, IDirectory directory)
        {
            request.Response.ResponseContent = new MemoryStream();
            StreamWriter writer = new StreamWriter(request.Response.ResponseContent);
            try
            {
                GridClient client;
                User user;
                // Get the session.

                StreamReader reader = new StreamReader(request.PostData);
                string qstring = reader.ReadToEnd();
                reader.Dispose();
                
                Dictionary<string, string> POST = deviMobile.PostDecode(qstring);
                Console.WriteLine("[LOGIN-DETAILS] Got a session: \"" + POST["session"] + "\"");
                Guid key = new Guid(POST["session"]);

                // Standard user stuff.
                lock (users)
                {
                    user = users[key];
                    client = user.Client;
                    user.LastRequest = DateTime.Now;
                    // Clear the inventory cache to avoid confusion.
                    user.Events.ClearInventory();
                }
                Hashtable data = new Hashtable();
                //string pos = (string)Math.Floor(client.Self.GlobalPosition.X / 256) + ", " + (string)Math.Floor(client.Self.GlobalPosition.Y / 256);
                //data.Add("RegionCoords", pos);

                data.Add("reg", client.Network.CurrentSim.Name);
                string pos = Math.Floor(client.Self.SimPosition.X) + "/" + Math.Round(client.Self.SimPosition.Y) + "/" + Math.Round(client.Self.SimPosition.Z);
                data.Add("pos", pos);
                data.Add("motd", client.Network.LoginMessage);
                data.Add("name", client.Self.FirstName + " " + client.Self.LastName);
                data.Add("id", client.Self.AgentID);
                //data.Add("InventoryRoot", client.Inventory.Store.RootFolder.UUID);
                
                writer.Write(MakeJson.FromHashtable(data));
                writer.Flush();
            }
            catch (Exception e)
            {
                writer.WriteLine("ERROR: " + e.Message);
            }
            writer.Flush();
        }

        #endregion

        #region IResource Members

        public string Name
        {
            get { return this.name; }
        }

        public IDirectory Parent
        {
            get { return this.parent; }
        }

        #endregion

        #region IDisposable Members

        public void Dispose()
        {
            // Nothing to do here.
        }

        #endregion
    }
}

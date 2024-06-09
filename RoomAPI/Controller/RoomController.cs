using Microsoft.AspNetCore.Mvc;
using System.Collections.Concurrent;

namespace RoomAPI
{
    [ApiController]
    [Route("[controller]")]
    public class RoomController : ControllerBase
    {
        private static ConcurrentDictionary<string, Room> Rooms = new ConcurrentDictionary<string, Room>();

        [HttpPost("createOrJoin")]
        public ActionResult CreateOrJoinRoom(string key, string memberId, double latitude, double longitude)
        {
            var member = new Member { Id = memberId, Latitude = latitude, Longitude = longitude };
            var room = Rooms.GetOrAdd(key, _ => new Room { Key = key });
            if (room.Members.Count < 5)
            {
                room.Members.AddOrUpdate(memberId, member, (_, _) => member);
                return Ok(room);
            }
            else
            {
                return BadRequest("Room is full.");
            }
        }

        [HttpPost("updateLocation")]
        public ActionResult UpdateLocation(string key, string memberId, double latitude, double longitude)
        {
            if (Rooms.TryGetValue(key, out var room))
            {
                if (room.Members.TryGetValue(memberId, out var member))
                {
                    member.Latitude = latitude;
                    member.Longitude = longitude;
                    return Ok();
                }
                return NotFound("Member not found.");
            }
            return NotFound("Room not found.");
        }

        [HttpGet("getMembersLocation")]
        public ActionResult GetMembersLocation(string key)
        {
            if (Rooms.TryGetValue(key, out var room))
            {
                return Ok(room.Members.Values);
            }
            return NotFound("Room not found.");
        }

        [HttpPost("leave")]
        public ActionResult LeaveRoom(string key, string memberId)
        {
            if (Rooms.TryGetValue(key, out var room))
            {
                room.Members.TryRemove(memberId, out _);
                if (room.Members.IsEmpty)
                {
                    Rooms.TryRemove(key, out _);
                }
                return Ok();
            }
            return NotFound("Room not found.");
        }
    }

    public class Room
    {
        public string Key { get; set; }
        public ConcurrentDictionary<string, Member> Members { get; } = new ConcurrentDictionary<string, Member>();
    }

    public class Member
    {
        public string Id { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
    }
}

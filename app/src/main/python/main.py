# My code is shit.
import taiwanbus
import asyncio
route_name = ""
paths_name = []
paths = []
db_updates = {}

def get_bus(routekey):
    global route_name, paths_name, paths
    paths = asyncio.run(taiwanbus.get_complete_bus_info(routekey))
    path = asyncio.run(taiwanbus.fetch_paths(routekey))
    for p in path:
        paths_name.append(p["path_name"])
    route = asyncio.run(taiwanbus.fetch_route(routekey))
    route_name = route[0]["route_name"]

def get_formated_info(route_key):
    info = asyncio.run(taiwanbus.get_complete_bus_info(route_key))
    return taiwanbus.format_bus_info(info)

def provider(provider):
    taiwanbus.update_provider(provider)

def check_database_update():
    global db_updates
    db_updates = taiwanbus.check_database_update()

# My code is shit.
import taiwanbus
import asyncio
route = ""
paths_name = []
paths = []

def get_bus(routekey):
    route = asyncio.run(taiwanbus.get_complete_bus_info(routekey))

def get_formated_info(route_key):
    info = asyncio.run(taiwanbus.get_complete_bus_info(route_key))
    return taiwanbus.format_bus_info(info)

def provider(provider):
    taiwanbus.update_provider(provider)

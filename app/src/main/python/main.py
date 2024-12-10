# My code is shit.
import taiwanbus
import asyncio

def get_formated_info(route_key):
    info = asyncio.run(taiwanbus.get_complete_bus_info(route_key))
    return taiwanbus.format_bus_info(info)


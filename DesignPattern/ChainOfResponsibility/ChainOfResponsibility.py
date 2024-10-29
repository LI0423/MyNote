from abc import ABCMeta, abstractmethod


class Method:
    def process_direct(self, *args, **kwargs):
        raise NotImplementedError
    
    def process(self, *args, **kwargs):
        return self.process_direct(*args, **kwargs)
    
    async def async_process_direct(self, *args, **kwargs):
        raise NotImplementedError
    
    async def async_process(self, *args, **kwargs):
        return await self.async_process_direct(*args, **kwargs)
    
class IFilter:
    def process(self, chain, method: Method, *args, **kwargs):
        raise NotImplementedError
    
    async def async_process(self, chain, method: Method, *args, **kwargs):
        raise NotImplementedError
    
class BeforeFilter(IFilter):
    def process(self, chain, method: Method, *args, **kwargs):
        self.before_process(*args, **kwargs)
        result = chain.process(*args, **kwargs)
        return result
    
    async def async_process(self, chain, method: Method, *args, **kwargs):
        await self.async_before_process(*args, **kwargs)
        result = await chain.async_process(*args, **kwargs)
        return result
    
    def before_process(self, *args, **kwargs):
        raise NotImplementedError
    
    async def async_before_process(self, *args, **kwargs):
        raise NotImplementedError
    
class AfterFilter(IFilter):
    def process(self, chain, method, *args, **kwargs):
        self.after_process(*args, **kwargs)
        result = chain.process(*args, **kwargs)
        return result
    
    async def async_process(self, chain, method: Method, *args, **kwargs):
        result = await chain.async_process(*args, **kwargs)
        await self.async_after_process(*args, **kwargs)
        return result
    
    def after_process(self, *args, **kwargs):
        raise NotImplementedError
    
    async def async_after_process(self, *args, **kwargs):
        raise NotImplementedError

class IFilterChain(metaclass=ABCMeta):
    @abstractmethod
    def next_filter(self):
        raise NotImplementedError
    
class FilterChain(IFilterChain):

    def __init__(self, method: Method):
        self.method: Method = method
        self.filters: list = []
        self.index: int = 0

    def add_filter(self, filter_obj: IFilter):
        if filter_obj:
            self.filters.append(filter_obj)

    def next_filter(self):
        filter_obj = self.filters[self.index]
        self.index += 1
        return filter_obj
    
    def process(self, *args, **kwargs):
        if self.index < len(self.filters):
            result = self.next_filter().process(self, self.method, *args, **kwargs)
        else:
            result = self.method.process(*args, **kwargs)
        return result
    
    async def async_process(self, *args, **kwargs):
        if self.index < len(self.filters):
            result = await self.next_filter().async_process(self, self.method, *args, **kwargs)
        else:
            result = await self.method.async_process(*args, **kwargs)
        return result
    
class Context:
    def __init__(self):
        self.value = []

class BusinessMethod(Method):
    def process_direct(self, context):
        context.value.append(2)
        return context
    
    async def async_process_direct(self, context):
        context.value.append(22)
        return context
    
class BusinessBeforeFilter(BeforeFilter):
    def before_process(self, context):
        context.value.append(1)

    async def async_before_process(self, context):
        context.value.append(11)

class BusinessAfterFilter(AfterFilter):
    def after_process(self, context):
        context.value.append(3)

    async def async_after_process(self, context):
        context.value.append(33)

def test_filter_chain_process():
    filter_chain = FilterChain(BusinessMethod())

    filter_chain.add_filter(BusinessBeforeFilter())
    filter_chain.add_filter(BusinessAfterFilter())

    context = Context()
    result = filter_chain.process(context)
    print(result.value)
    assert result.value == [2, 1, 3]

async def test_filter_chain_awaitable_process(event_loop):
    filter_chain = FilterChain(BusinessMethod())

    filter_chain.add_filter(BusinessBeforeFilter())
    filter_chain.add_filter(BusinessAfterFilter())

    context = Context()
    result = await filter_chain.async_process(context)
    assert result.value == [11, 22, 33]

test_filter_chain_process()
test_filter_chain_awaitable_process()
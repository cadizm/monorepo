from cadizm.foo import FOO


def test_ipdb():
    import ipdb

    ipdb.set_trace()
    print("here")


def say_hello():
    return f"hello {FOO}"


print(say_hello())

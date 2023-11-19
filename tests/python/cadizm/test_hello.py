import unittest

from cadizm.hello import say_hello


class HelloTestCase(unittest.TestCase):
    def test_something(self):
        self.assertEqual("hello FOO", say_hello())


if __name__ == "__main__":
    unittest.main()

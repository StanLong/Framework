**1. jQuery 库中的 $() 是什么？**

$() 函数是 jQuery() 函数的别称

**2. $(this) 和 this 关键字在 jQuery 中有何不同**

$(this) 返回一个 jQuery 对象，你可以对它调用多个 jQuery 方法，比如用 text() 获取文本，用val() 获取值等等。而 this 代表当前元素，它是 JavaScript 关键词中的一个，表示上下文中的当前 DOM 元素。你不能对它调用 jQuery 方法，直到它被 $() 函数包裹，例如 $(this)


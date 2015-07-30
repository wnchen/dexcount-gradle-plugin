package com.getkeepsafe.dexcount

import spock.lang.Specification

class PackageTreeTest extends Specification {
    def "adding duplicates increments count"() {
        setup:
        def tree = new PackageTree()
        tree.addMethodRef("com.foo.Bar")

        when:
        tree.addMethodRef("com.foo.Bar")

        then:
        tree.getMethodCount() == 2
    }

    def "can print a package list with classes included"() {
        setup:
        def writer = new StringBuilder()
        def tree = new PackageTree()

        when:
        tree.addMethodRef("com.foo.Bar")
        tree.addMethodRef("com.foo.Bar")
        tree.addMethodRef("com.foo.Qux")
        tree.addMethodRef("com.alpha.Beta")

        tree.printPackageListWithClasses(writer)

        then:
        writer.toString() == """4        com
1        com.alpha
1        com.alpha.Beta
3        com.foo
2        com.foo.Bar
1        com.foo.Qux
"""
    }

    def "can print a package list without classes"() {
        setup:
        def writer = new StringBuilder()
        def tree = new PackageTree()

        when:
        tree.addMethodRef("com.foo.Bar")
        tree.addMethodRef("com.foo.Bar")
        tree.addMethodRef("com.foo.Qux")
        tree.addMethodRef("com.alpha.Beta")

        tree.printPackageListWithoutClasses(writer)

        then:
        writer.toString() == """4        com
1        com.alpha
3        com.foo
"""
    }

    def "can print a tree"() {
        setup:
        def sb = new StringBuilder()
        def tree = new PackageTree()

        when:
        tree.addMethodRef("com.foo.Bar")
        tree.addMethodRef("com.foo.Bar")
        tree.addMethodRef("com.foo.Qux")
        tree.addMethodRef("com.alpha.Beta")

        tree.printTree(sb, true)

        then:
        sb.toString() == """com (4)
  alpha (1)
    Beta (1)
  foo (3)
    Bar (2)
    Qux (1)
"""
    }
}

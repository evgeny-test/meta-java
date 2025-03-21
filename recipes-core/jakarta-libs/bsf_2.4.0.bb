SUMMARY = "Bean Scripting Framework package"
AUTHOR = "Apache Software Foundation"
LICENSE = "Apache-2.0"
PR = "r2"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=b1e01b26bacfc2232046c90a330332b3"

SRC_URI = "https://dlcdn.apache.org/commons/bsf/source/bsf-src-${PV}.tar.gz"

PACKAGE_ARCH = "${TUNE_PKGARCH}"

inherit java-library

DEPENDS = "jacl commons-logging rhino xalan-j bcel"

do_compile() {
  mkdir -p build

  oe_makeclasspath cp -s commons-logging jacl rhino bcel xalan2

	# Remove netrexx and jython support
  rm -Rf src/org/apache/bsf/engines/netrexx
  rm -Rf src/org/apache/bsf/engines/jython

  javac -sourcepath src -cp $cp -d build `find src -name "*.java"`

  fastjar cf ${JARFILENAME} -C build .
}


SRC_URI[sha256sum] = "5ab58cf5738c144f4d85a4a442c2f33be2c4c502dca6e29e0c570c2a51ae6ae9"

BBCLASSEXTEND = "native"


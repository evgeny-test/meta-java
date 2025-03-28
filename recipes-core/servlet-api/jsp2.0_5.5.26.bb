SUMMARY = "Java Server Page (JSP) API 2.0 (from Tomcat 5.5)"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"
AUTHOR = "Apache Software Foundation"

SRC_URI = "http://archive.apache.org/dist/tomcat/tomcat-5/v${PV}/src/apache-tomcat-${PV}-src.tar.gz"

S = "${WORKDIR}/apache-tomcat-${PV}-src/servletapi/jsr152"

inherit java-library

DEPENDS = "servlet2.4"
RDEPENDS:${PN} = "libservlet2.4-java"

# Value of implementation.revision in build.xml
IMPL_REVISION = "public_draft"

JARFILENAME = "jsp-api-2.0.${IMPL_REVISION}.jar"
ALTJARFILENAMES = "jsp-api-2.0.jar jsp-api.jar"

do_unpackpost[dirs] = "${B}"
do_unpackpost() {
  sed -i -e "s|@implementation.version@|${IMPL_REVISION}|" src/etc/manifest
}

addtask unpackpost after do_unpack before do_patch

do_compile() {
  mkdir -p build

  oe_makeclasspath cp -s servlet-api-2.4
  javac -sourcepath src/share -d build -cp $cp `find src/share/javax -name \*.java`

  # Copy extraneous files according to build.xml's compile task
  (cd src/share && find . -name "*.properties" -exec cp {} ../../build/{} \;)

  mkdir -p build/javax/servlet/jsp/resources
  cp src/share/dtd/jsp*.dtd build/javax/servlet/jsp/resources
  cp src/share/dtd/jsp*.xsd build/javax/servlet/jsp/resources
  cp src/share/dtd/web-jsp*.dtd build/javax/servlet/jsp/resources
  cp src/share/dtd/web-jsp*.xsd build/javax/servlet/jsp/resources

  fastjar -C build -c -m src/etc/manifest -f ${JARFILENAME} .
}

SRC_URI[sha256sum] = "ddc677d7391c438e6102d0b3e9653eaca661344ef74b0260c1f488340d660395"

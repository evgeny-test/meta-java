SUMMARY = "a Java wrapper including a basic JDBC driver for SQLite"

DESCRIPTION = "\
This is a Java wrapper including a basic JDBC driver for the SQLite 2/3 \
database engine. It is designed using JNI to interface to the SQLite API. \
That API is wrapped by methods in the SQLite.Database class."

LICENSE = "MIT-Modern-Variant"
SECTION = "application"
PR = "r1"
SRC_URI =  " \
    http://www.ch-werner.de/javasqlite/${BPN}-${PV}.tar.gz \
    file://0001-fix-errors-during-cross-compile.patch \
    "

LIC_FILES_CHKSUM = "file://license.terms;md5=3da0e50feac5f64f08184584a8cad58a"

inherit autotools-brokensep

DEPENDS += "sqlite3 sqlite3-native icedtea7-native"
RDEPENDS:${PN} += "sqlite3"
FILES:${PN} +="/usr/share"

PARALLEL_MAKE = "-j 1"

SRC_URI[sha256sum] = "b6b26e2c1a2174f5525d904eb3ff698c3f7089f0d4b4c84cf6121da08c8d801f"

EXTRA_OECONF = " \
    --with-sqlite3=${STAGING_DIR_NATIVE}/usr \
    --with-sqlite3-target=${STAGING_DIR_TARGET}/usr \
    --with-jdk=${STAGING_DIR_NATIVE}/usr/lib/jvm/icedtea7-native/ \
    "

EXTRA_OEMAKE = "DESTDIR=${D}"

# This dev package contains an '.so' file used for JNI development
INSANE_SKIP:${PN}-dev = "dev-elf"

do_compile:prepend (){
	echo '#!/bin/sh' > ${STAGING_BINDIR_NATIVE}/cc
	echo '${BUILD_CC} $@' >> ${STAGING_BINDIR_NATIVE}/cc
	chmod a+x ${STAGING_BINDIR_NATIVE}/cc
}

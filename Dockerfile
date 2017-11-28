FROM denvazh/scala:2.11.6-openjdk8
ADD target/**/scraper-vdm.jar /
EXPOSE 8080
ENV LANG=fr_FR.UTF-8
CMD scala /scraper-vdm.jar
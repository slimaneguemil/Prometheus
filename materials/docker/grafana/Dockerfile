FROM grafana/grafana:5.2.1

COPY docker/grafana/datasource-prometheus.yaml /etc/grafana/provisioning/datasources/
COPY docker/grafana/dashboard-provider.yaml /etc/grafana/provisioning/dashboards/
COPY docker/grafana/ps-monitoring-dashboard.json /var/lib/grafana/dashboards/

COPY docker/grafana/init.sh .
RUN ./init.sh
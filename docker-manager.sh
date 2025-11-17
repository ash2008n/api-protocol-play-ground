#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${GREEN}╔══════════════════════════════════════════════════╗${NC}"
echo -e "${GREEN}║   API Protocol Playground - Docker Manager      ║${NC}"
echo -e "${GREEN}╚══════════════════════════════════════════════════╝${NC}"
echo ""

# Function to start services
start_services() {
    echo -e "${YELLOW}🚀 Starting Kafka and RabbitMQ services...${NC}"
    docker compose up -d
    
    echo ""
    echo -e "${GREEN}✅ Services started successfully!${NC}"
    echo ""
    echo "📊 Service URLs:"
    echo "  🟢 Kafka:                localhost:9092"
    echo "  🟢 Kafka UI:             http://localhost:8080"
    echo "  🟢 RabbitMQ AMQP:        localhost:5672"
    echo "  🟢 RabbitMQ Management:  http://localhost:15672 (guest/guest)"
    echo ""
    echo "💡 Tip: Run 'docker compose logs -f' to view logs"
}

# Function to stop services
stop_services() {
    echo -e "${YELLOW}🛑 Stopping all services...${NC}"
    docker compose down
    echo -e "${GREEN}✅ Services stopped!${NC}"
}

# Function to stop and remove volumes
clean_services() {
    echo -e "${YELLOW}🧹 Stopping services and removing volumes...${NC}"
    docker compose down -v
    echo -e "${GREEN}✅ Services stopped and volumes removed!${NC}"
}

# Function to show status
show_status() {
    echo -e "${YELLOW}📊 Service Status:${NC}"
    docker compose ps
}

# Function to show logs
show_logs() {
    echo -e "${YELLOW}📋 Showing logs (Ctrl+C to exit)...${NC}"
    docker compose logs -f
}

# Function to restart services
restart_services() {
    echo -e "${YELLOW}🔄 Restarting services...${NC}"
    docker compose restart
    echo -e "${GREEN}✅ Services restarted!${NC}"
}

# Main menu
case "$1" in
    start)
        start_services
        ;;
    stop)
        stop_services
        ;;
    clean)
        clean_services
        ;;
    status)
        show_status
        ;;
    logs)
        show_logs
        ;;
    restart)
        restart_services
        ;;
    *)
        echo "Usage: $0 {start|stop|clean|status|logs|restart}"
        echo ""
        echo "Commands:"
        echo "  start    - Start Kafka and RabbitMQ services"
        echo "  stop     - Stop all services"
        echo "  clean    - Stop services and remove volumes"
        echo "  status   - Show service status"
        echo "  logs     - Show service logs (follow mode)"
        echo "  restart  - Restart all services"
        exit 1
        ;;
esac

exit 0
